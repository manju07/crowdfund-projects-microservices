package com.crowdfund.projects.microservices.projectservice.dao.impl;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.entity.Transaction;
import com.crowdfund.projects.microservices.common.code.entity.Wallet;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContributeDAOImplTest {

    @InjectMocks
    private ContributeDAOImpl contributeDAO;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private WalletRepository walletRepository;

    @Test
    void saveContribute_InvalidUserAndProject_ThrowsCustomException_Test() throws CustomException, ResourceNotFoundException {

        Project project = TestUtils.getProject();

        when(userRepository.findByUserName(TestUtils.USERNAME))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(TestUtils.getUser()));

        when(projectRepository.findById(project.getId()))
                .thenReturn(Optional.empty());

        CustomException customException = assertThrows(CustomException.class, () -> contributeDAO.saveContribute(getTransaction(),1L,  new OAuth2Authentication(null, TestUtils.getUserAuthentication())));

        assertEquals("Invalid User", customException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, customException.getStatus());

        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> contributeDAO.saveContribute(getTransaction(),1L,  new OAuth2Authentication(null, TestUtils.getUserAuthentication())));

        assertEquals("Project does not exist with id = 1", resourceNotFoundException.getMessage());

        verify(userRepository, times(2)).findByUserName(TestUtils.USERNAME);
        verify(projectRepository).findById(project.getId());
    }

    @Test
    void saveContribute_ProjectStatusCompleted_ThrowsCustomException_Test() throws CustomException, ResourceNotFoundException {

        Project project = TestUtils.getProject();
        project.setStatus(ProjectStatus.COMPLETED);

        when(userRepository.findByUserName(TestUtils.USERNAME))
                .thenReturn(Optional.of(TestUtils.getUser()));

        when(projectRepository.findById(project.getId()))
                .thenReturn(Optional.of(project));

        CustomException customException = assertThrows(CustomException.class, () -> contributeDAO.saveContribute(getTransaction(),1L,  new OAuth2Authentication(null, TestUtils.getUserAuthentication())));

        assertEquals("Project is completed", customException.getMessage());
        assertEquals("This project already received requested money", customException.getDescription());

        assertEquals(HttpStatus.BAD_REQUEST, customException.getStatus());
    }


    @Test
    void saveContribute_WalletNotFound_ThrowsCustomException_Test() throws CustomException, ResourceNotFoundException {

        Project project = TestUtils.getProject();

        when(userRepository.findByUserName(TestUtils.USERNAME))
                .thenReturn(Optional.of(TestUtils.getUser()));

        when(projectRepository.findById(project.getId()))
                .thenReturn(Optional.of(project));

        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.empty());

        CustomException customException = assertThrows(CustomException.class, () -> contributeDAO.saveContribute(getTransaction(),1L,  new OAuth2Authentication(null, TestUtils.getUserAuthentication())));
        assertEquals("Wallet not found", customException.getDescription());
        assertEquals(HttpStatus.NOT_FOUND, customException.getStatus());
    }

    @Test
    void saveContribute_DebitTransactionAmountGreaterThanWalletBalance_ThrowsCustomException_Test() throws CustomException, ResourceNotFoundException {

        Project project = TestUtils.getProject();

        when(userRepository.findByUserName(TestUtils.USERNAME))
                .thenReturn(Optional.of(TestUtils.getUser()));

        when(projectRepository.findById(project.getId()))
                .thenReturn(Optional.of(project));

        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.of(getWallet()));

        Transaction transaction = getTransaction();
        transaction.setAmount(1001);

        CustomException customException = assertThrows(CustomException.class, () -> contributeDAO.saveContribute(transaction,1L,  new OAuth2Authentication(null, TestUtils.getUserAuthentication())));

        assertEquals("Insufficient balance", customException.getMessage());
        assertEquals("You don't have required money in your wallet", customException.getDescription());
        assertEquals(HttpStatus.BAD_REQUEST, customException.getStatus());
    }

    private static Transaction getTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(100);
        transaction.setProject(TestUtils.getProject());
        return transaction;
    }

    private static Wallet getWallet() {
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(1000F);
        wallet.setUser(TestUtils.getUser());
        return wallet;
    }
}