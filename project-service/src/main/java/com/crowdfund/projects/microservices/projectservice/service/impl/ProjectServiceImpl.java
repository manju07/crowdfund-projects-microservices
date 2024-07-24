package com.crowdfund.projects.microservices.projectservice.service.impl;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.dto.ProjectReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.ProjectResDTO;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.dao.ProjectDAO;
import com.crowdfund.projects.microservices.projectservice.mapper.ProjectMapper;
import com.crowdfund.projects.microservices.projectservice.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private static final ProjectMapper MAPPER = ProjectMapper.INSTANCE;

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    public ProjectResDTO addProject(ProjectReqDTO projectReqDTO, OAuth2Authentication authentication) throws CustomException, ResourceNotFoundException {
        try {
            Project project = MAPPER.projectReqDTOToProject(projectReqDTO);
            Project responseProject = projectDAO.addProject(project, authentication);
            ProjectResDTO projectResDTO = MAPPER.projectToProjectResDTO(responseProject);
            return projectResDTO;
        } catch (Exception e) {
            log.error("addProject method exception", e);
            throw e;
        }
    }

    @Override
    public ProjectResDTO updateProject(ProjectReqDTO projectReqDTO) throws ResourceNotFoundException, CustomException {
        return null;
//        try {
//            Project project = MAPPER.projectReqDTOToProject(projectReqDTO);
//            Project responseProject = projectDAO.updateProject(project);
//            ProjectResDTO projectResDTO = MAPPER.projectToProjectResDTO(responseProject);
//            return projectResDTO;
//        } catch (Exception e) {
//            log.error("updateProject method exception", e);
//            throw e;
//        }
    }

    @Override
    public boolean deleteProject(Long projectId) throws ResourceNotFoundException, CustomException {
        try {
            return projectDAO.deleteProject(projectId);
        } catch (Exception e) {
            log.error("deleteProject method exception", e);
            throw e;
        }
    }

    @Override
    public ProjectResDTO getProjectById(Long projectId) throws ResourceNotFoundException, CustomException {
        try {
            Project project = projectDAO.getProjectById(projectId);
            ProjectResDTO projectResDTO = MAPPER.projectToProjectResDTO(project);
            return projectResDTO;
        } catch (Exception e) {
            log.error("getProjectById method exception", e);
            throw e;
        }
    }

    @Override
    public Page<ProjectResDTO> getAll(ProjectStatus projectStatus, int offset, int limit) throws ResourceNotFoundException, CustomException {
        try {
            Page<Project> pageProject = projectDAO.getAll(projectStatus, offset, limit);
            List<ProjectResDTO> projectResDTOS = MAPPER.convert(pageProject.getContent());
            PageImpl<ProjectResDTO> pageImpl = new PageImpl<>(projectResDTOS, pageProject.getPageable(), pageProject.getTotalElements());
            return pageImpl;
        } catch (Exception e) {
            log.error("getAll method exception", e);
            throw e;
        }
    }
}
