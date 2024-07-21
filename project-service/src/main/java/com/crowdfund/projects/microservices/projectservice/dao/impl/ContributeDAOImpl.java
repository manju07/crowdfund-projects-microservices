package com.crowdfund.projects.microservices.projectservice.dao.impl;

import com.crowdfund.projects.microservices.common.code.constant.PaymentStatus;
import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.constant.TransactionType;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.entity.Transaction;
import com.crowdfund.projects.microservices.common.code.entity.User;
import com.crowdfund.projects.microservices.common.code.entity.Wallet;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.common.code.util.UniqueIDGenerator;
import com.crowdfund.projects.microservices.projectservice.dao.ContributeDAO;
import com.crowdfund.projects.microservices.projectservice.repository.ProjectRepository;
import com.crowdfund.projects.microservices.projectservice.repository.TransactionRepository;
import com.crowdfund.projects.microservices.projectservice.repository.UserRepository;
import com.crowdfund.projects.microservices.projectservice.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
public class ContributeDAOImpl implements ContributeDAO {


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private WalletRepository walletRepository;


    @Override
    @Transactional
    public Transaction saveContribute(Transaction transaction, Long projectId, OAuth2Authentication authentication) throws CustomException, ResourceNotFoundException {
        try {
            String userName = authentication.getName();
            Optional<User> userOptional = userRepository.findByUserName(userName);
            User user = userOptional.get();

            Optional<Project> projectOptional = projectRepository.findById(projectId);

            if (!projectOptional.isPresent()) {
                log.debug("Project does not exist with id:{}", projectId);
                throw new ResourceNotFoundException("Project does not exist with id = " + projectId);
            }

            Project project = projectOptional.get();

            if (!ProjectStatus.IN_PROGRESS.equals(project.getStatus())) {
                log.debug("Project status is incorrect,  ProjectId:{}, ProjectStatus:{}", projectId, project.getStatus());
                throw new CustomException("Invalid Project status", HttpStatus.BAD_REQUEST, "Project not in valid state");
            }

            String transactionId = UniqueIDGenerator.generateUniqueID();

            Transaction debitTransaction = debitMoneyFromDonorWallet(transaction, userName, user, project, transactionId);
            Transaction creditTransaction = creditMoneyToAdminWallet(debitTransaction, userName);
            log.debug("username:{}, debitTransaction:{}", userName, debitTransaction);
            log.debug("username:{}, creditTransaction:{}", userName, creditTransaction);
            return debitTransaction;
        } catch (Exception e) {
            log.error("ContributeDAOImpl.saveContribute() exception", e);
            throw e;
        }
    }

    private Transaction debitMoneyFromDonorWallet(Transaction debitTransaction, String userName, User user, Project project, String transactionId) throws CustomException {

        Optional<Wallet> donorWalletOptional = walletRepository.findByUserId(user.getId());
        if (!donorWalletOptional.isPresent()) {
            log.debug("Wallet not found for username:{}", userName);
            throw new CustomException("Invalid Wallet", HttpStatus.NOT_FOUND, "Wallet not found");
        }

        Wallet donorWallet = donorWalletOptional.get();
        Float balance = donorWallet.getBalance();

        if (debitTransaction.getAmount() > balance) {
            log.debug("Insufficient balance for username:{}", userName);
            throw new CustomException("Insufficient balance", HttpStatus.BAD_REQUEST, "You don't have required money in your wallet");
        }

        balance = balance - debitTransaction.getAmount();
        donorWallet.setBalance(balance);
        donorWallet.addTransaction(debitTransaction);

        debitTransaction.setWallet(donorWallet);
        debitTransaction.setProject(project);
        debitTransaction.setTransactionType(TransactionType.DEBIT);
        debitTransaction.setCreatedBy(userName);
        debitTransaction.setUpdatedBy(userName);
        debitTransaction.setTransactionId(transactionId);

        log.info("amount debited from donor wallet username:{} ", userName);

        float receivedAmount = project.getReceivedAmount() + debitTransaction.getAmount();
        project.setReceivedAmount(receivedAmount);

        if (project.getReceivedAmount() >= project.getRequiredAmount()) {
            project.setStatus(ProjectStatus.COMPLETED);
        }

        project.addTransaction(debitTransaction);

        return transactionRepository.save(debitTransaction);
    }

    private Transaction creditMoneyToAdminWallet(Transaction debitTransaction, String userName) {
        Optional<Wallet> adminWalletOptional = walletRepository.findByUserId(1L);
        Wallet adminWallet = adminWalletOptional.get();

        Transaction creditTransaction = new Transaction();
        creditTransaction.setTransactionType(TransactionType.CREDIT);
        creditTransaction.setProject(debitTransaction.getProject());
        creditTransaction.setWallet(adminWallet);
        creditTransaction.setAmount(debitTransaction.getAmount());
        creditTransaction.setPaymentStatus(PaymentStatus.SUCCESS);
        creditTransaction.setTransactionId(debitTransaction.getTransactionId());
        creditTransaction.setCreatedBy(userName);
        creditTransaction.setUpdatedBy(userName);

        Float adminBalance = adminWallet.getBalance();
        adminWallet.setBalance(adminBalance + debitTransaction.getAmount());
        adminWallet.addTransaction(creditTransaction);

        return transactionRepository.save(creditTransaction);
    }
}
