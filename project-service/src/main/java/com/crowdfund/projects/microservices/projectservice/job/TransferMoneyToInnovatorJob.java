package com.crowdfund.projects.microservices.projectservice.job;

import com.crowdfund.projects.microservices.common.code.constant.PaymentStatus;
import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.constant.TransactionType;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.entity.Transaction;
import com.crowdfund.projects.microservices.common.code.entity.User;
import com.crowdfund.projects.microservices.common.code.entity.Wallet;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.util.UniqueIDGenerator;
import com.crowdfund.projects.microservices.projectservice.repository.ProjectRepository;
import com.crowdfund.projects.microservices.projectservice.repository.TransactionRepository;
import com.crowdfund.projects.microservices.projectservice.repository.UserRepository;
import com.crowdfund.projects.microservices.projectservice.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class TransferMoneyToInnovatorJob {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;


    @Autowired
    private TransactionRepository transactionRepository;

    @Scheduled(cron = "*/5 * * * * ?")
    public void transferMoneyToInnovator() throws InterruptedException, CustomException {
        try {
            log.info(
                    "Scheduler running - Transfer Money to innovator  - " + System.currentTimeMillis() / 1000);

            Page<Project> completedProjects = projectRepository.findAllByStatus(ProjectStatus.COMPLETED, PageRequest.of(0, 10));
            List<Project> listOfProjects = completedProjects.getContent();
            for (Project project : listOfProjects) {
                transferMoney(project);
            }
        } catch (Exception e) {
            log.error("caught exception", e);
        }
    }

    @Transactional()
    private void transferMoney(Project project) throws CustomException {
        try {
            log.debug("Running transferMoney()");
            if (!ProjectStatus.COMPLETED.equals(project.getStatus())) {
                log.info("Project-Id:" + project.getId() + " is not in COMPLETED state");
                return;
            }

            String transactionId = UniqueIDGenerator.generateUniqueID();
            Transaction debitTransactionFromAdminWallet = debitMoneyFromAdminWallet(project, transactionId);
            Transaction creditTransactionToInnovatorWallet = creditMoneyToInnovatorWallet(debitTransactionFromAdminWallet, project);

            log.debug("debitTransactionFromAdminWallet:{}", debitTransactionFromAdminWallet);
            log.debug("creditTransactionToInnovatorWallet:{}", creditTransactionToInnovatorWallet);

            project.setStatus(ProjectStatus.ARCHIEVED);
            projectRepository.save(project);
        } catch (CustomException e) {
            log.error("CustomException", e);
            throw e;
        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    private Transaction debitMoneyFromAdminWallet(Project project, String transactionId) throws CustomException {
        log.debug("Running debitMoneyFromAdminWallet()");
        Optional<Wallet> adminWalletOptional = walletRepository.findByUserId(1L);
        Wallet adminWallet = adminWalletOptional.get();

        Float adminBalance = adminWallet.getBalance();
        float deductingAmount = project.getReceivedAmount();
        if (adminBalance < deductingAmount) {
            log.debug("Admin does not have sufficient balance");
            throw new CustomException("Admin does not have sufficient balance");
        }

        adminBalance = adminBalance - deductingAmount;
        adminWallet.setBalance(adminBalance);

        Transaction debitTransaction = new Transaction();
        debitTransaction.setTransactionType(TransactionType.DEBIT);
        debitTransaction.setProject(project);
        debitTransaction.setAmount(deductingAmount);
        debitTransaction.setPaymentStatus(PaymentStatus.SUCCESS);
        debitTransaction.setTransactionId(transactionId);
        debitTransaction.setCreatedBy("CRONJOB");
        debitTransaction.setUpdatedBy("CRONJOB");
        debitTransaction.setWallet(adminWallet);


        adminWallet.addTransaction(debitTransaction);
        project.addTransaction(debitTransaction);

        try {
            walletRepository.save(adminWallet);
            return transactionRepository.save(debitTransaction);
        } catch (Exception e) {
            throw e;
        }
    }

    private Transaction creditMoneyToInnovatorWallet(Transaction debitTransaction, Project project) throws CustomException {
        log.debug("Running creditMoneyToInnovatorWallet()");
        User user = project.getUser();

        if (Objects.isNull(user)) {
            log.debug("Project is not mapped to any innovator, project:{}", project);
            throw new CustomException("Project is not mapped to any innovator");
        }

        Optional<User> userOptional = userRepository.findById(user.getId());
        user = userOptional.get();
        String userName = user.getUserName();

        Optional<Wallet> innovatorWalletOptional = walletRepository.findByUserId(user.getId());
        if (!innovatorWalletOptional.isPresent()) {
            log.debug("Wallet not found for username:{}", userName);
            throw new CustomException("Wallet not found");
        }

        Wallet innovatorWallet = innovatorWalletOptional.get();
        Float balance = innovatorWallet.getBalance();

        balance = balance + debitTransaction.getAmount();
        innovatorWallet.setBalance(balance);

        Transaction creditTransaction = new Transaction();
        creditTransaction.setTransactionType(TransactionType.CREDIT);
        creditTransaction.setProject(debitTransaction.getProject());
        creditTransaction.setWallet(innovatorWallet);
        creditTransaction.setAmount(debitTransaction.getAmount());
        creditTransaction.setPaymentStatus(PaymentStatus.SUCCESS);
        creditTransaction.setTransactionId(debitTransaction.getTransactionId());
        creditTransaction.setCreatedBy("CRONJOB");
        creditTransaction.setUpdatedBy("CRONJOB");

        debitTransaction.getProject().addTransaction(creditTransaction);

        innovatorWallet.addTransaction(creditTransaction);
        log.info("amount credited to innovator wallet username:{} ", userName);

        try {
            walletRepository.save(innovatorWallet);
            return transactionRepository.save(creditTransaction);
        } catch (Exception e) {
            throw e;
        }
    }

}
