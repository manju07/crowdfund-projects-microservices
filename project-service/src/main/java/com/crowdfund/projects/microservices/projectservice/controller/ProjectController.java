package com.crowdfund.projects.microservices.projectservice.controller;

import com.crowdfund.projects.microservices.projectservice.mapper.ProjectMapper;
import com.crowdfund.projects.microservices.projectservice.service.ProjectService;
import com.crowdfund.projects.microservices.common.code.dto.ProjectReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.ProjectResDTO;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/project", produces = "application/json")
@Api(value = "Sample-App", description = "Project operations")
@Validated
@Slf4j
public class ProjectController {
    private static final ProjectMapper MAPPER = ProjectMapper.INSTANCE;
    @Autowired
    private ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_INNOVATOR')")
    @ApiOperation(value = "create project", response = ProjectResDTO.class)
    public ResponseEntity<ProjectResDTO> createProject(@Valid @RequestBody ProjectReqDTO projectReqDTO)
            throws CustomException, ResourceNotFoundException {
        log.info("calling create project API");
        Project project = MAPPER.projectReqDTOToProject(projectReqDTO);
        Project responseProject = projectService.addProject(project);
        ProjectResDTO projectResDTO = MAPPER.projectToProjectResDTO(responseProject);
        return new ResponseEntity<>(projectResDTO, HttpStatus.CREATED);
    }

}
