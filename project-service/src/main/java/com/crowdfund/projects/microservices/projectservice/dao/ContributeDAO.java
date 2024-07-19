package com.crowdfund.projects.microservices.projectservice.dao;

import com.crowdfund.projects.microservices.common.code.entity.Contribute;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;

public interface ContributeDAO {
    Contribute saveContribute(Contribute contribute, Long projectId) throws CustomException, ResourceNotFoundException;

//    Project updateProject(Project project);
//
//    boolean deleteProject(Long projectId);
//
//    Project getProjectById(Long projectId) throws ResourceNotFoundException;
//
//    Page<Project> getAll(int offset, int limit);

}
