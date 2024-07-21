package com.crowdfund.projects.microservices.projectservice.service;

import com.crowdfund.projects.microservices.common.code.dto.TransactionReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.TransactionResDTO;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

@Service
public interface ContributeService {
    TransactionResDTO saveContribute(TransactionReqDTO transactionReqDTO, OAuth2Authentication authentication) throws CustomException, ResourceNotFoundException;
}
