package com.crowdfund.projects.microservices.projectservice.controller;

import com.crowdfund.projects.microservices.common.code.constant.PaymentStatus;
import com.crowdfund.projects.microservices.common.code.dto.TransactionReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.TransactionResDTO;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.TestUtils;
import com.crowdfund.projects.microservices.projectservice.service.ContributeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContributeControllerTest {
    @Mock
    private ContributeService contributeService;

    @InjectMocks
    private ContributeController contributeController;

    @Test
    void saveContributeTest() throws CustomException, ResourceNotFoundException {

        when(contributeService.saveContribute(any(TransactionReqDTO.class), any(OAuth2Authentication.class)))
                .thenReturn(getTransactionResDTO());

        ResponseEntity<TransactionResDTO> transactionResDTOResponseEntity = contributeController.saveContribute(getTransactionReqDTO(), new OAuth2Authentication(null, TestUtils.getUserAuthentication()));

        assertNotNull(transactionResDTOResponseEntity);
        assertEquals(HttpStatus.OK, transactionResDTOResponseEntity.getStatusCode());
        TransactionResDTO transactionResDTO = transactionResDTOResponseEntity.getBody();

        assertNotNull(transactionResDTO);
        assertEquals(1, transactionResDTO.getId());
        assertEquals(1, transactionResDTO.getProjectId());
        assertEquals(100, transactionResDTO.getAmount());
        assertEquals(PaymentStatus.SUCCESS, transactionResDTO.getPaymentStatus());
    }

    private static TransactionResDTO getTransactionResDTO() {
        TransactionResDTO transactionResDTO = new TransactionResDTO();
        transactionResDTO.setAmount(100);
        transactionResDTO.setId(1L);
        transactionResDTO.setPaymentStatus(PaymentStatus.SUCCESS);
        transactionResDTO.setProjectId(1L);
        return transactionResDTO;
    }

    private static TransactionReqDTO getTransactionReqDTO() {
        TransactionReqDTO transactionReqDTO = new TransactionReqDTO();
        transactionReqDTO.setProjectId(1L);
        transactionReqDTO.setAmount(100);
        return transactionReqDTO;
    }
}