package com.crowdfund.projects.microservices.projectservice.dao.impl;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.ProjectServiceTestUtils;
import com.crowdfund.projects.microservices.projectservice.repository.ProjectRepository;
import com.crowdfund.projects.microservices.projectservice.repository.UserRepository;
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
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectDAOImplTest {


    @InjectMocks
    private ProjectDAOImpl projectDAO;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addProjectTest() throws CustomException {

        Project project = ProjectServiceTestUtils.getProject();

        when(userRepository.findByUserName(ProjectServiceTestUtils.USERNAME))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(ProjectServiceTestUtils.getUser()))
                .thenReturn(Optional.of(ProjectServiceTestUtils.getUser()))
                .thenReturn(Optional.of(ProjectServiceTestUtils.getUser()));

        when(projectRepository.findByName(project.getName()))
                .thenReturn(Optional.of(project))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.empty());

        when(projectRepository.save(any(Project.class)))
                .thenThrow(new RuntimeException("Internal server error"))
                .thenReturn(project);

        CustomException customException = assertThrows(CustomException.class, () -> projectDAO.addProject(project, new OAuth2Authentication(null, ProjectServiceTestUtils.getUserAuthentication())));

        assertEquals("Invalid User", customException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, customException.getStatus());

        customException = assertThrows(CustomException.class, () -> projectDAO.addProject(project, new OAuth2Authentication(null, ProjectServiceTestUtils.getUserAuthentication())));

        assertEquals("name exist", customException.getMessage());
        assertEquals(HttpStatus.CONFLICT, customException.getStatus());

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> projectDAO.addProject(project, new OAuth2Authentication(null, ProjectServiceTestUtils.getUserAuthentication())));

        assertEquals("Internal server error", runtimeException.getMessage());

        Project savedProject = projectDAO.addProject(project, new OAuth2Authentication(null, ProjectServiceTestUtils.getUserAuthentication()));

        assertNotNull(savedProject);
        assertEquals(1L, savedProject.getId());
        assertEquals(ProjectStatus.IN_PROGRESS, savedProject.getStatus());
        assertEquals(1000, savedProject.getRequiredAmount());
        assertEquals(0, savedProject.getReceivedAmount());

        verify(userRepository, times(4)).findByUserName(ProjectServiceTestUtils.USERNAME);
        verify(projectRepository, times(3)).findByName(project.getName());
        verify(projectRepository, times(2)).save(any(Project.class));
    }

    @Test
    void deleteProjectTest() {
        doNothing().when(projectRepository).deleteById(1L);
        boolean result = projectDAO.deleteProject(1L);
        assertTrue(result);
    }

    @Test
    void deleteProject_ThrowsException_Test() {
        doThrow(new RuntimeException("Internal server error")).when(projectRepository).deleteById(1L);
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> projectDAO.deleteProject(1L));

        assertEquals("Internal server error", runtimeException.getMessage());
        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    void getProjectByIdTest() throws ResourceNotFoundException {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(ProjectServiceTestUtils.getProject()));
        Project projectById = projectDAO.getProjectById(1L);

        assertNotNull(projectById);
        assertEquals(1L, projectById.getId());
        assertEquals(ProjectStatus.IN_PROGRESS, projectById.getStatus());
        assertEquals(1000, projectById.getRequiredAmount());
        assertEquals(0, projectById.getReceivedAmount());
    }

    @Test
    void getProjectById_ThrowsResourceNotFoundException_Test() throws ResourceNotFoundException {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> projectDAO.getProjectById(1L));

        assertEquals("Project does not exist with id = 1", resourceNotFoundException.getMessage());

        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void getAllProjectsTest() {
        PageImpl<Project> pageImpl = new PageImpl<>(Collections.singletonList(ProjectServiceTestUtils.getProject()), PageRequest.of(0, 1), 1);

        when(projectRepository.findAllByStatus(ProjectStatus.IN_PROGRESS, PageRequest.of(0, 1)))
                .thenThrow(new RuntimeException("Internal server error"))
                .thenReturn(pageImpl);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> projectDAO.getAll(ProjectStatus.IN_PROGRESS, 0, 1));
        assertEquals("Internal server error", runtimeException.getMessage());

        Page<Project> page = projectDAO.getAll(ProjectStatus.IN_PROGRESS, 0, 1);
        assertNotNull(page);

        assertNotNull(page.getContent());
        assertEquals(1, page.getContent().size());
        assertEquals("Project-1", page.getContent().get(0).getName());
        assertEquals(1000, page.getContent().get(0).getRequiredAmount());

        assertEquals(1, page.getTotalElements());
        assertEquals(1, page.getTotalPages());
        assertEquals(0, page.getPageable().getOffset());
        assertEquals(1, page.getPageable().getPageSize());

        verify(projectRepository, times(2)).findAllByStatus(ProjectStatus.IN_PROGRESS, PageRequest.of(0, 1));
    }

    @Test
    void getAllProjects_ThrowsException_Test() {
        when(projectRepository.findAllByStatus(ProjectStatus.IN_PROGRESS, PageRequest.of(0, 1)))
                .thenReturn(Page.empty());
        Page<Project> page = projectDAO.getAll(ProjectStatus.IN_PROGRESS, 0, 1);
        assertNotNull(page);
        assertNotNull(page.getContent());

        verify(projectRepository).findAllByStatus(ProjectStatus.IN_PROGRESS, PageRequest.of(0, 1));
    }


}