package com.crowdfund.projects.microservices.projectservice.dao;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface ProjectDAO {
    Project addProject(Project project, OAuth2Authentication authentication) throws CustomException;

    boolean deleteProject(Long projectId);

    Project getProjectById(Long projectId) throws ResourceNotFoundException;

    Page<Project> getAll(ProjectStatus projectStatus, int offset, int limit);

}
