package com.crowdfund.projects.microservices.projectservice.service;

import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project Service
 *
 * @author Manjunath Asundi
 */
@Service
public interface ProjectService {
    Project  addProject(Project project) throws CustomException, ResourceNotFoundException;

    Project updateProject(Project project) throws ResourceNotFoundException, CustomException;

    boolean deleteProject(Long projectId) throws ResourceNotFoundException, CustomException;

    Project getProjectById(Long projectId) throws ResourceNotFoundException, CustomException;

    List<Project> getAll(int offset, int limit) throws ResourceNotFoundException, CustomException;
}