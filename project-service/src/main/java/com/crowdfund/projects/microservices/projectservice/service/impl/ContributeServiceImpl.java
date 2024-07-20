package com.crowdfund.projects.microservices.projectservice.service.impl;

import com.crowdfund.projects.microservices.common.code.dto.TransactionReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.TransactionResDTO;
import com.crowdfund.projects.microservices.common.code.entity.Transaction;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.dao.ContributeDAO;
import com.crowdfund.projects.microservices.projectservice.mapper.TransactionMapper;
import com.crowdfund.projects.microservices.projectservice.service.ContributeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContributeServiceImpl implements ContributeService {

    private static final TransactionMapper MAPPER = TransactionMapper.INSTANCE;

    @Autowired
    private ContributeDAO contributeDAO;

    @Override
    public TransactionResDTO saveContribute(TransactionReqDTO transactionReqDTO) throws CustomException, ResourceNotFoundException {
        try {
            Transaction transaction = MAPPER.transactionReqDTOToTransaction(transactionReqDTO);
            Transaction savedTransaction = contributeDAO.saveContribute(transaction, transactionReqDTO.getProjectId());
            TransactionResDTO transactionResDTO = MAPPER.transactionToTransactionResDTO(savedTransaction);
            return transactionResDTO;
        } catch (Exception e) {
            log.error("saveContribute method exception", e);
            throw e;
        }
    }
}
