package com.crowdfund.projects.microservices.projectservice.dao.impl;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.constant.TransactionType;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.entity.Transaction;
import com.crowdfund.projects.microservices.common.code.entity.User;
import com.crowdfund.projects.microservices.common.code.entity.Wallet;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.dao.ContributeDAO;
import com.crowdfund.projects.microservices.projectservice.repository.*;
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

            if (!userOptional.isPresent())
                throw new CustomException("Invalid User", HttpStatus.NOT_FOUND, "User does not exist");
            User user = userOptional.get();
//            contribute.setUser(user);

            Optional<Project> projectOptional = projectRepository.findById(projectId);

            if (!projectOptional.isPresent())
                throw new ResourceNotFoundException("Project does not exist with id = " + projectId);

            Project project = projectOptional.get();

            if (!ProjectStatus.IN_PROGRESS.equals(project.getStatus()))
                throw new CustomException("Invalid Project status", HttpStatus.BAD_REQUEST, "Project not in valid state");

            Optional<Wallet> walletOptional = walletRepository.findByUserId(user.getId());
            if (!walletOptional.isPresent()) {
                throw new CustomException("Invalid Wallet", HttpStatus.NOT_FOUND, "Wallet not found");
            }

            Wallet wallet = walletOptional.get();
            Float balance = wallet.getBalance();

            if (transaction.getAmount() > balance) {
                throw new CustomException("Insufficient balance", HttpStatus.BAD_REQUEST, "You don't have required money in your wallet");
            }

            balance = balance - transaction.getAmount();
            wallet.setBalance(balance);
            wallet.addTransaction(transaction);
            log.info("amount deducted user:{} from wallet", userName);

            float receivedAmount = project.getReceivedAmount() + transaction.getAmount();
            project.setReceivedAmount(receivedAmount);

            if (project.getReceivedAmount() >= project.getRequiredAmount() ) {
                project.setStatus(ProjectStatus.COMPLETED);
            }

            project.addTransaction(transaction);

            transaction.setProject(project);
            transaction.setWallet(wallet);
            transaction.setTransactionType(TransactionType.DEBIT);
//            user.addContribute(transaction);

            transaction.setCreatedBy(userName);
            transaction.setUpdatedBy(userName);

            return transactionRepository.save(transaction);
        } catch (Exception e) {
            log.error("ContributeDAOImpl exception", e);
            throw e;
        }
    }
}
