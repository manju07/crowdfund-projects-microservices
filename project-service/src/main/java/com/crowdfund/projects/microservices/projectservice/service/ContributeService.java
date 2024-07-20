package com.crowdfund.projects.microservices.projectservice.service;

import com.crowdfund.projects.microservices.common.code.dto.*;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface ContributeService {
    TransactionResDTO saveContribute(TransactionReqDTO transactionReqDTO) throws CustomException, ResourceNotFoundException;

//    ProjectResDTO updateProject(ProjectReqDTO project) throws ResourceNotFoundException, CustomException;
//
//    boolean deleteProject(Long projectId) throws ResourceNotFoundException, CustomException;
//
//    ProjectResDTO getProjectById(Long projectId) throws ResourceNotFoundException, CustomException;
//
//    Page<Project> getAll(int offset, int limit) throws ResourceNotFoundException, CustomException;
}
