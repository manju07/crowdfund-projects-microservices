package com.crowdfund.projects.microservices.projectservice.mapper;

import com.crowdfund.projects.microservices.common.code.constant.PaymentStatus;
import com.crowdfund.projects.microservices.common.code.dto.TransactionReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.TransactionResDTO;
import com.crowdfund.projects.microservices.common.code.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "paymentStatus", constant = "SUCCESS", resultType = PaymentStatus.class)
    Transaction transactionReqDTOToTransaction(TransactionReqDTO transactionReqDTO);

    TransactionResDTO transactionToTransactionResDTO(Transaction transaction);

}
