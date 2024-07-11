package com.crowdfund.projects.microservices.projectservice.dao;

import com.crowdfund.projects.microservices.common.code.dto.ProjectResDTO;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;

import java.util.List;

public interface ProjectDAO {
    Project addProject(Project project);

    Project updateProject(Project project);

    boolean deleteProject(Long projectId);

    Project getProjectById(Long projectId) throws ResourceNotFoundException;

    List<Project> getAll(int offset, int limit);

}
