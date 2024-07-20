package com.crowdfund.projects.microservices.projectservice.dao.impl;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.entity.User;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.dao.ProjectDAO;
import com.crowdfund.projects.microservices.projectservice.repository.ProjectRepository;
import com.crowdfund.projects.microservices.projectservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class ProjectDAOImpl implements ProjectDAO {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public Project addProject(Project project, OAuth2Authentication authentication) throws CustomException {
        try {
            String userName = authentication.getName();
            Optional<User> user = userRepository.findByUserName(userName);

            if (!user.isPresent())
                throw new CustomException("Invalid User", HttpStatus.NOT_FOUND, "User doesn't exist");

            Optional<Project> existingProject = projectRepository.findByName(project.getName());
            if (existingProject.isPresent()) {
                throw new CustomException("name exist", HttpStatus.CONFLICT, "Project name already exist");
            }

            project.setUser(user.get());
            project.setCreatedBy(userName);
            project.setUpdatedBy(userName);
            Project response = projectRepository.save(project);
            return response;
        } catch (Exception e) {
            log.error("ProjectDAOImpl -  addProject exception", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Project updateProject(Project project) {
//        try {
//            String userName = UserData.getUserName();
//            project.setUpdatedBy(userName);
//            Project response = projectRepository.save(project);
//            return response;
//        } catch (Exception e) {
//            log.error("ProjectDAOImpl -  updateProject exception", e);
//            throw e;
//        }
        return null;
    }

    @Override
    @Transactional
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
            throw new ResourceNotFoundException("Project does not exist with id = " + projectId);
        } catch (Exception e) {
            log.error("ProjectDAOImpl -  deleteProject exception", e);
            throw e;
        }
    }

    @Override
    public Page<Project> getAll(ProjectStatus projectStatus, int offset, int limit) {
        try {
            Page<Project> projectPage = projectRepository.findAllByStatus(projectStatus, PageRequest.of(offset, limit));

            if (Objects.nonNull(projectPage) && (!projectPage.isEmpty())) {
                return projectPage;
            }
            log.info("Project list is empty");
            return Page.empty();
        } catch (Exception e) {
            log.error("ProjectDAOImpl -  deleteProject exception", e);
            throw e;
        }
    }
}
