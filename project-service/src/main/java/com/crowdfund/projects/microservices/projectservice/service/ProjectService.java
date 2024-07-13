package com.crowdfund.projects.microservices.projectservice.service;

import com.crowdfund.projects.microservices.common.code.dto.ProjectReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.ProjectResDTO;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Project Service
 *
 * @author Manjunath Asundi
 */
@Service
public interface ProjectService {
    ProjectResDTO addProject(ProjectReqDTO project) throws CustomException, ResourceNotFoundException;

    ProjectResDTO updateProject(ProjectReqDTO project) throws ResourceNotFoundException, CustomException;

    boolean deleteProject(Long projectId) throws ResourceNotFoundException, CustomException;

    ProjectResDTO getProjectById(Long projectId) throws ResourceNotFoundException, CustomException;

    Page<Project> getAll(int offset, int limit) throws ResourceNotFoundException, CustomException;
}