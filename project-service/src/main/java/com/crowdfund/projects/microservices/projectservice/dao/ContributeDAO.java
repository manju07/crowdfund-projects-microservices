package com.crowdfund.projects.microservices.projectservice.dao;

import com.crowdfund.projects.microservices.common.code.entity.Transaction;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface ContributeDAO {
    Transaction saveContribute(Transaction transaction, Long projectId, OAuth2Authentication authentication) throws CustomException, ResourceNotFoundException;

//    Project updateProject(Project project);
//
//    boolean deleteProject(Long projectId);
//
//    Project getProjectById(Long projectId) throws ResourceNotFoundException;
//
//    Page<Project> getAll(int offset, int limit);

}
