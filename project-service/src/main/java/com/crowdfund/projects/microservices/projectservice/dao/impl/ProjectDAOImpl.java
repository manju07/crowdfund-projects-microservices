package com.crowdfund.projects.microservices.projectservice.dao.impl;

import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.dao.ProjectDAO;
import com.crowdfund.projects.microservices.projectservice.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class ProjectDAOImpl implements ProjectDAO  {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project addProject(Project project) {
        try {
            Project response = projectRepository.save(project);
            return response;
        } catch (Exception e) {
            log.error("ProjectDAOImpl -  addProject exception", e);
            throw e;
        }
    }

    @Override
    public Project updateProject(Project project) {
        try {
            Project response = projectRepository.save(project);
            return response;
        } catch (Exception e) {
            log.error("ProjectDAOImpl -  updateProject exception", e);
            throw e;
        }
    }

    @Override
    public boolean deleteProject(Long projectId) {
        try {
            projectRepository.deleteById(projectId);
            return true;
        } catch (Exception e) {
            log.error("ProjectDAOImpl -  deleteProject exception", e);
            throw e;
        }
    }

    @Override
    public Project getProjectById(Long projectId) throws ResourceNotFoundException {
        try {
            Optional<Project> optionalProject = projectRepository.findById(projectId);
            if (optionalProject.isPresent()) {
                return optionalProject.get();
            }
            throw new ResourceNotFoundException("Project does not exist with id" + projectId);
        } catch (Exception e) {
            log.error("ProjectDAOImpl -  deleteProject exception", e);
            throw e;
        }
    }

    @Override
    public List<Project> getAll(int offset, int limit) {
        try {
            List<Project> projectList = projectRepository.findAll();
            if (Objects.nonNull(projectList) && (!projectList.isEmpty()) ) {
                return projectList;
            }
            log.info("Project list is empty");
            return Arrays.asList();
        } catch (Exception e) {
            log.error("ProjectDAOImpl -  deleteProject exception", e);
            throw e;
        }
    }
}
