package com.crowdfund.projects.microservices.projectservice.service.impl;

import com.crowdfund.projects.microservices.common.code.dto.ContributeReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.ContributeResDTO;
import com.crowdfund.projects.microservices.common.code.dto.ProjectResDTO;
import com.crowdfund.projects.microservices.common.code.entity.Contribute;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.dao.ContributeDAO;
import com.crowdfund.projects.microservices.projectservice.mapper.ContributeMapper;
import com.crowdfund.projects.microservices.projectservice.service.ContributeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContributeServiceImpl implements ContributeService {

    private static final ContributeMapper MAPPER = ContributeMapper.INSTANCE;

    @Autowired
    private ContributeDAO contributeDAO;

    @Override
    public ContributeResDTO saveContribute(ContributeReqDTO contributeReqDTO) throws CustomException, ResourceNotFoundException {
        try {
            Contribute contribute = MAPPER.contributeReqDTOToProject(contributeReqDTO);
            Contribute savedContribute = contributeDAO.saveContribute(contribute, contributeReqDTO.getProjectId());
            ContributeResDTO contributeResDTO = MAPPER.contributeToContributeResDTO(savedContribute);
            return contributeResDTO;
        } catch (Exception e) {
            log.error("addProject method exception", e);
            throw e;
        }
    }
}
