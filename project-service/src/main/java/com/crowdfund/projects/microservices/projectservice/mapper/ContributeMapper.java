/*
package com.crowdfund.projects.microservices.projectservice.mapper;

import com.crowdfund.projects.microservices.common.code.constant.PaymentStatus;
import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.dto.ContributeReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.ContributeResDTO;
import com.crowdfund.projects.microservices.common.code.dto.ProjectReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.ProjectResDTO;
import com.crowdfund.projects.microservices.common.code.entity.Contribute;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContributeMapper {
    ContributeMapper INSTANCE = Mappers.getMapper(ContributeMapper.class);

    @Mapping(target = "paymentStatus", constant = "SUCCESS", resultType = PaymentStatus.class)
    Contribute contributeReqDTOToProject(ContributeReqDTO contributeReqDTO);

    ContributeResDTO contributeToContributeResDTO(Contribute project);

}
*/
