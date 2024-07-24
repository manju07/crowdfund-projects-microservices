package com.crowdfund.projects.microservices.projectservice.job;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.constant.TransactionType;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.entity.Transaction;
import com.crowdfund.projects.microservices.common.code.entity.Wallet;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.projectservice.TestUtils;
import com.crowdfund.projects.microservices.projectservice.repository.ProjectRepository;
import com.crowdfund.projects.microservices.projectservice.repository.TransactionRepository;
import com.crowdfund.projects.microservices.projectservice.repository.UserRepository;
import com.crowdfund.projects.microservices.projectservice.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static com.crowdfund.projects.microservices.projectservice.TestUtils.getTransaction;
import static com.crowdfund.projects.microservices.projectservice.TestUtils.getWallet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferMoneyToInnovatorJobTest {

    @InjectMocks
    private TransferMoneyToInnovatorJob transferMoneyToInnovatorJob;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void transferMoneyToInnovator_ProjectIsNotInCompletedState_Test() throws CustomException, InterruptedException {
        Project project = TestUtils.getProject();

        PageImpl<Project> pageImpl = new PageImpl<>(Collections.singletonList(project), PageRequest.of(0, 1), 1);

        when(projectRepository.findAllByStatus(ProjectStatus.COMPLETED, PageRequest.of(0, 10)))
                .thenReturn(pageImpl);

        CustomException customException = assertThrows(CustomException.class, () -> transferMoneyToInnovatorJob.transferMoneyToInnovator());

        assertEquals("Project status is not in COMPLETED state", customException.getMessage());

    }

    @Test
    void transferMoneyToInnovator_AdminBalanceLessThanReceivedAmountForProject_Test() throws CustomException, InterruptedException {
        Project project = TestUtils.getProject();
        project.setStatus(ProjectStatus.COMPLETED);
        project.setReceivedAmount(100);

        PageImpl<Project> pageImpl = new PageImpl<>(Collections.singletonList(project), PageRequest.of(0, 1), 1);

        when(projectRepository.findAllByStatus(ProjectStatus.COMPLETED, PageRequest.of(0, 10)))
                .thenReturn(pageImpl);

        Wallet adminWallet = getWallet();
        adminWallet.setBalance(10F);
        Wallet innovatorWallet = getWallet();

        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.of(adminWallet))
                .thenReturn(Optional.of(innovatorWallet));

        CustomException customException = assertThrows(CustomException.class, () -> transferMoneyToInnovatorJob.transferMoneyToInnovator());

        assertEquals("Admin does not have sufficient balance", customException.getMessage());
    }


    @Test
    void transferMoneyToInnovator_ProjectIsNotMappedToInnovator_ThrowsCustomException_Test() throws CustomException, InterruptedException {
        Project project = TestUtils.getProject();
        project.setStatus(ProjectStatus.COMPLETED);

        PageImpl<Project> pageImpl = new PageImpl<>(Collections.singletonList(project), PageRequest.of(0, 1), 1);

        when(projectRepository.findAllByStatus(ProjectStatus.COMPLETED, PageRequest.of(0, 10)))
                .thenReturn(pageImpl);

        Wallet adminWallet = getWallet();
        Wallet innovatorWallet = getWallet();

        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.of(adminWallet))
                .thenReturn(Optional.of(innovatorWallet));

        Transaction debitTransaction = getTransaction();
        debitTransaction.setTransactionType(TransactionType.DEBIT);

        Transaction creditTransaction = getTransaction();
        creditTransaction.setTransactionType(TransactionType.CREDIT);

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(debitTransaction)
                .thenReturn(creditTransaction);

        when(walletRepository.save(any(Wallet.class)))
                .thenReturn(adminWallet)
                .thenReturn(innovatorWallet);

        CustomException customException = assertThrows(CustomException.class, () -> transferMoneyToInnovatorJob.transferMoneyToInnovator());

        assertEquals("Project is not mapped to any innovator", customException.getMessage());
    }

    @Test
    void transferMoneyToInnovator_InnovatorWalletNotFound_ThrowsCustomException_Test() throws CustomException, InterruptedException {
        Project project = TestUtils.getProject();
        project.setStatus(ProjectStatus.COMPLETED);
        project.setUser(TestUtils.getUser());

        PageImpl<Project> pageImpl = new PageImpl<>(Collections.singletonList(project), PageRequest.of(0, 1), 1);

        when(projectRepository.findAllByStatus(ProjectStatus.COMPLETED, PageRequest.of(0, 10)))
                .thenReturn(pageImpl);

        Wallet adminWallet = getWallet();
        Wallet innovatorWallet = getWallet();

        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.of(adminWallet))
                .thenReturn(Optional.empty());

        Transaction debitTransaction = getTransaction();
        debitTransaction.setTransactionType(TransactionType.DEBIT);

        Transaction creditTransaction = getTransaction();
        creditTransaction.setTransactionType(TransactionType.CREDIT);

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(debitTransaction)
                .thenReturn(creditTransaction);

        when(walletRepository.save(any(Wallet.class)))
                .thenReturn(adminWallet)
                .thenReturn(innovatorWallet);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(TestUtils.getUser()));

        CustomException customException = assertThrows(CustomException.class, () -> transferMoneyToInnovatorJob.transferMoneyToInnovator());

        assertEquals("Innovator Wallet not found", customException.getMessage());
    }

    @Test
    void transferMoneyToInnovator_Success_Test() throws CustomException, InterruptedException {
        Project project = TestUtils.getProject();
        project.setStatus(ProjectStatus.COMPLETED);
        project.setUser(TestUtils.getUser());

        PageImpl<Project> pageImpl = new PageImpl<>(Collections.singletonList(project), PageRequest.of(0, 1), 1);

        when(projectRepository.findAllByStatus(ProjectStatus.COMPLETED, PageRequest.of(0, 10)))
                .thenReturn(pageImpl);

        Wallet adminWallet = getWallet();
        Wallet innovatorWallet = getWallet();

        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.of(adminWallet))
                .thenReturn(Optional.of(innovatorWallet));

        Transaction debitTransaction = getTransaction();
        debitTransaction.setTransactionType(TransactionType.DEBIT);

        Transaction creditTransaction = getTransaction();
        creditTransaction.setTransactionType(TransactionType.CREDIT);

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(debitTransaction)
                .thenReturn(creditTransaction);

        when(walletRepository.save(any(Wallet.class)))
                .thenReturn(adminWallet)
                .thenReturn(innovatorWallet);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(TestUtils.getUser()));

        when(projectRepository.save(any(Project.class)))
                .thenReturn(project);

        transferMoneyToInnovatorJob.transferMoneyToInnovator();
    }
}