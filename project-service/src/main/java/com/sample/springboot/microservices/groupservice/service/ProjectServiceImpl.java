package com.sample.springboot.microservices.groupservice.service;

import com.sample.springboot.microservices.common.code.entity.Project;
import com.sample.springboot.microservices.common.code.exception.CustomException;
import com.sample.springboot.microservices.common.code.exception.ResourceNotFoundException;
import com.sample.springboot.microservices.groupservice.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService  {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project addProject(Project project) throws CustomException, ResourceNotFoundException {
        try {
            return projectRepository.save(project);
        } catch (Exception e) {
            log.error("addProject method exception", e);
            throw e;
        }
    }

    @Override
    public Project updateProject(Project project) throws ResourceNotFoundException, CustomException {
        try {
            return projectRepository.save(project);
        } catch (Exception e) {
            log.error("addProject method exception", e);
            throw e;
        }
    }

    @Override
    public boolean deleteProject(Long projectId) throws ResourceNotFoundException, CustomException {
        try {
            projectRepository.deleteById(projectId);
            return true;
        } catch (Exception e) {
            log.error("deleteProject method exception", e);
            throw e;
        }
    }

    @Override
    public Project getProjectById(Long projectId) throws ResourceNotFoundException, CustomException {
        try {
            return projectRepository.findById(projectId).get();
        } catch (Exception e) {
            log.error("getProjectById method exception", e);
            throw e;
        }
    }

    @Override
    public List<Project> getAll(int offset, int limit) throws ResourceNotFoundException, CustomException {
        try {
            return projectRepository.findAll();
        } catch (Exception e) {
            log.error("getProjectById method exception", e);
            throw e;
        }
    }
}
