package com.crowdfund.projects.microservices.projectservice.service.impl;

import com.crowdfund.projects.microservices.common.code.constant.PaymentStatus;
import com.crowdfund.projects.microservices.common.code.constant.TransactionType;
import com.crowdfund.projects.microservices.common.code.dto.TransactionReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.TransactionResDTO;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.entity.Transaction;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.dao.ContributeDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContributeServiceImplTest {

    @InjectMocks
    private ContributeServiceImpl contributeService;


    @Mock
    private ContributeDAO contributeDAO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveContributeTest() throws CustomException, ResourceNotFoundException {
        when(contributeDAO.saveContribute(any(Transaction.class), any(Long.class), any()))
                .thenReturn(getTransaction());
        TransactionResDTO transactionResDTO = contributeService.saveContribute(getTransactionReqDTO(), null);
        assertNotNull(transactionResDTO);
        assertEquals(1L, transactionResDTO.getId());
        assertEquals(100, transactionResDTO.getAmount());
        assertEquals(PaymentStatus.SUCCESS, transactionResDTO.getPaymentStatus());

        verify(contributeDAO, times(1)).saveContribute(any(Transaction.class), any(Long.class), any());
    }

    @Test
    public void saveContribute_ThrowsCustomException_Test() throws CustomException, ResourceNotFoundException {
        when(contributeDAO.saveContribute(any(Transaction.class), any(Long.class), any()))
                .thenThrow(new CustomException("Internal server error"));

        TransactionReqDTO transactionReqDTO = getTransactionReqDTO();
        CustomException customException = assertThrows(CustomException.class, () -> {
            contributeService.saveContribute(transactionReqDTO, null);
        });

        assertEquals("Internal server error", customException.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, customException.getStatus());

        verify(contributeDAO, times(1)).saveContribute(any(Transaction.class), any(Long.class), any());
    }

    private Transaction getTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setAmount(100);
        transaction.setProject(getProject());
        transaction.setPaymentStatus(PaymentStatus.SUCCESS);
        return transaction;
    }

    private Project getProject() {
        Project project = new Project();
        project.setId(1L);
        project.setRequiredAmount(1000);
        project.setReceivedAmount(100);
        return project;
    }

    private TransactionReqDTO getTransactionReqDTO() {
        TransactionReqDTO transactionReqDTO = new TransactionReqDTO();
        transactionReqDTO.setAmount(100);
        transactionReqDTO.setProjectId(1L);
        return transactionReqDTO;
    }
}
