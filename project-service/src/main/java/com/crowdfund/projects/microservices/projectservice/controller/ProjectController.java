package com.crowdfund.projects.microservices.projectservice.controller;

import com.crowdfund.projects.microservices.common.code.dto.ProjectReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.ProjectResDTO;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.service.ProjectService;
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
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/project", produces = "application/json")
@Api(value = "Project-App", description = "Project operations")
@Validated
@Slf4j
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_INNOVATOR')")
    @ApiOperation(value = "createProject", response = ProjectResDTO.class)
    public ResponseEntity<ProjectResDTO> createProject(@Valid @RequestBody ProjectReqDTO projectReqDTO)
            throws CustomException, ResourceNotFoundException {
        log.info("calling create project API");
        ProjectResDTO projectResDTO = projectService.addProject(projectReqDTO);
        return new ResponseEntity<>(projectResDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_INNOVATOR')")
    @ApiOperation(value = "getProjectById", response = ProjectResDTO.class)
    public ResponseEntity<ProjectResDTO> getProjectById(@RequestHeader Long projectID)
            throws CustomException, ResourceNotFoundException {
        log.info("calling getProjectById API");
        ProjectResDTO projectResDTO = projectService.getProjectById(projectID);
        return new ResponseEntity<>(projectResDTO, HttpStatus.OK);
    }

    @GetMapping
//    @PreAuthorize("hasRole('ROLE_INNOVATOR')")
    @ApiOperation(value = "getAllProjects", response = ProjectResDTO.class)
    public ResponseEntity<List<ProjectResDTO>> getAllProjects(@RequestParam int offset, @RequestParam int limit)
            throws CustomException, ResourceNotFoundException {
        log.info("calling getAllProjects API");
        List<ProjectResDTO> projectResDTOList = projectService.getAll(offset, limit);
        return new ResponseEntity<>(projectResDTOList, HttpStatus.OK);
    }

}
