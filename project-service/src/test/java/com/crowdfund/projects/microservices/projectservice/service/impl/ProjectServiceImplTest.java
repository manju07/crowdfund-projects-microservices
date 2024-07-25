package com.crowdfund.projects.microservices.projectservice.service.impl;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.dto.ProjectReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.ProjectResDTO;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.ProjectServiceTestUtils;
import com.crowdfund.projects.microservices.projectservice.dao.ProjectDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectDAO projectDAO;


    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addProjectTest() throws CustomException, ResourceNotFoundException {
        when(projectDAO.addProject(any(Project.class), any()))
                .thenReturn(ProjectServiceTestUtils.getProject());
        ProjectResDTO projectResDTO = projectService.addProject(getProjectReqDTO(), null);

        assertNotNull(projectResDTO);
        assertEquals(1L, projectResDTO.getId());
        assertEquals(ProjectStatus.IN_PROGRESS, projectResDTO.getStatus());
        assertEquals(1000, projectResDTO.getRequiredAmount());
        assertEquals(0, projectResDTO.getReceivedAmount());

        verify(projectDAO, times(1)).addProject(any(Project.class), any());
    }

    @Test
    public void addProject_ThrowsCustomException_Test() throws CustomException {
        when(projectDAO.addProject(any(Project.class), any()))
                .thenThrow(new CustomException("Internal server error"));
        CustomException customException = assertThrows(CustomException.class, () -> projectService.addProject(getProjectReqDTO(), null));

        assertEquals("Internal server error", customException.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, customException.getStatus());
        verify(projectDAO, times(1)).addProject(any(Project.class), any());
    }

    @Test
    void deleteProjectTest() throws CustomException, ResourceNotFoundException {
        when(projectDAO.deleteProject(1L)).thenReturn(true);
        boolean result = projectService.deleteProject(1L);

        assertTrue(result);
        verify(projectDAO, times(1)).deleteProject(1L);
    }

    @Test
    void getProjectByIdTest() throws CustomException, ResourceNotFoundException {
        when(projectDAO.getProjectById(1L)).thenReturn(ProjectServiceTestUtils.getProject());
        ProjectResDTO projectResDTO = projectService.getProjectById(1L);

        assertNotNull(projectResDTO);
        assertEquals(1L, projectResDTO.getId());
        assertEquals(ProjectStatus.IN_PROGRESS, projectResDTO.getStatus());
        assertEquals(1000, projectResDTO.getRequiredAmount());
        assertEquals(0, projectResDTO.getReceivedAmount());

        verify(projectDAO, times(1)).getProjectById(1L);
    }

    @Test
    void getAllProjectsByTest() throws CustomException, ResourceNotFoundException {
        PageImpl<Project> pageImpl = new PageImpl<>(Collections.singletonList(ProjectServiceTestUtils.getProject()), PageRequest.of(0, 1), 1);
        when(projectDAO.getAll(ProjectStatus.IN_PROGRESS, 0, 1))
                .thenReturn(pageImpl);
        Page<ProjectResDTO> pageResponse = projectService.getAll(ProjectStatus.IN_PROGRESS, 0, 1);

        assertNotNull(pageResponse);
        assertNotNull(pageResponse.getContent());
        assertEquals(1, pageResponse.getContent().size());
        assertEquals("Project-1", pageResponse.getContent().get(0).getName());
        assertEquals(1000, pageResponse.getContent().get(0).getRequiredAmount());

        assertEquals(1, pageResponse.getTotalElements());
        assertEquals(1, pageResponse.getTotalPages());
        assertEquals(0, pageResponse.getPageable().getOffset());
        assertEquals(1, pageResponse.getPageable().getPageSize());

        verify(projectDAO, times(1)).getAll(ProjectStatus.IN_PROGRESS, 0, 1);
    }

    private ProjectReqDTO getProjectReqDTO() {
        ProjectReqDTO projectReqDTO = new ProjectReqDTO();
        projectReqDTO.setName("Project-1");
        projectReqDTO.setDescription("AI based analytics model");
        projectReqDTO.setRequiredAmount(1000);
        return projectReqDTO;
    }
}