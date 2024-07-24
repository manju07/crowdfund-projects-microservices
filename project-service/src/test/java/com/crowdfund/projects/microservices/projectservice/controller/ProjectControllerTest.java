package com.crowdfund.projects.microservices.projectservice.controller;

import com.crowdfund.projects.microservices.common.code.constant.PaymentStatus;
import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.dto.ProjectReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.ProjectResDTO;
import com.crowdfund.projects.microservices.common.code.dto.TransactionResDTO;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.TestUtils;
import com.crowdfund.projects.microservices.projectservice.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @Test
    void createProject() throws CustomException, ResourceNotFoundException {

        when(projectService.addProject(any(ProjectReqDTO.class), any(OAuth2Authentication.class)))
                .thenReturn(getProjectResDTO());

        ResponseEntity<ProjectResDTO> projectResDTOResponseEntity = projectController.createProject(getProjectReqDTO(), new OAuth2Authentication(null, TestUtils.getUserAuthentication()));

        assertNotNull(projectResDTOResponseEntity);
        assertEquals(HttpStatus.CREATED, projectResDTOResponseEntity.getStatusCode());
        ProjectResDTO projectResDTO = projectResDTOResponseEntity.getBody();

        assertNotNull(projectResDTO);
        assertEquals(1, projectResDTO.getId());
        assertEquals("Project-1", projectResDTO.getName());
        assertEquals("AI based analytics tool", projectResDTO.getDescription());
        assertEquals(ProjectStatus.IN_PROGRESS, projectResDTO.getStatus());
    }

    private static ProjectReqDTO getProjectReqDTO() {
        ProjectReqDTO projectReqDTO = new ProjectReqDTO();
        projectReqDTO.setName("Project-1");
        projectReqDTO.setDescription("AI based analytics tool");
        projectReqDTO.setRequiredAmount(100);
        return projectReqDTO;
    }

    private static ProjectResDTO getProjectResDTO() {
        ProjectResDTO projectResDTO = new ProjectResDTO();
        projectResDTO.setName("Project-1");
        projectResDTO.setDescription("AI based analytics tool");
        projectResDTO.setRequiredAmount(100);
        projectResDTO.setReceivedAmount(0);
        projectResDTO.setId(1L);
        projectResDTO.setStatus(ProjectStatus.IN_PROGRESS);
        return projectResDTO;
    }

    @Test
    void getProjectById() throws CustomException, ResourceNotFoundException {
        when(projectService.getProjectById(1L)).thenReturn(getProjectResDTO());
        ResponseEntity<ProjectResDTO> projectResDTOResponseEntity = projectController.getProjectById(1L);

        assertNotNull(projectResDTOResponseEntity);
        assertEquals(HttpStatus.OK, projectResDTOResponseEntity.getStatusCode());
        ProjectResDTO projectResDTO = projectResDTOResponseEntity.getBody();

        assertNotNull(projectResDTO);
        assertEquals(1, projectResDTO.getId());
        assertEquals("Project-1", projectResDTO.getName());
        assertEquals("AI based analytics tool", projectResDTO.getDescription());
        assertEquals(ProjectStatus.IN_PROGRESS, projectResDTO.getStatus());
    }

    @Test
    void getAllProjects() throws CustomException, ResourceNotFoundException {
        PageImpl<ProjectResDTO> pageImpl = new PageImpl<>(Collections.singletonList(getProjectResDTO()), PageRequest.of(0, 1), 1);

        when(projectService.getAll(ProjectStatus.IN_PROGRESS, 0, 1)).thenReturn(pageImpl);

        ResponseEntity<Page<ProjectResDTO>> pageResponseEntity = projectController.getAllProjects(ProjectStatus.IN_PROGRESS, 0, 1);

        assertNotNull(pageResponseEntity);
        assertEquals(HttpStatus.OK, pageResponseEntity.getStatusCode());
        Page<ProjectResDTO> pageResponseEntityBody = pageResponseEntity.getBody();

        assertNotNull(pageResponseEntityBody);
        assertNotNull(pageResponseEntityBody.getContent());
        assertEquals(1, pageResponseEntityBody.getContent().size());
        assertEquals("AI based analytics tool", pageResponseEntityBody.getContent().get(0).getDescription());
    }
}