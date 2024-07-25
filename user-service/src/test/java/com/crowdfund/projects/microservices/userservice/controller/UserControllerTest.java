package com.crowdfund.projects.microservices.userservice.controller;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.constant.UserRole;
import com.crowdfund.projects.microservices.common.code.dto.ProjectResDTO;
import com.crowdfund.projects.microservices.common.code.dto.UserReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.UserResDTO;
import com.crowdfund.projects.microservices.common.code.entity.Role;
import com.crowdfund.projects.microservices.common.code.entity.User;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.userservice.UserServiceTestUtils;
import com.crowdfund.projects.microservices.userservice.service.UserService;
import com.crowdfund.projects.microservices.userservice.service.impl.UserServiceImpl;
import org.apache.commons.math.stat.inference.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userService;

    @Test
    void donorSignUp() throws CustomException, ResourceNotFoundException {
        when(userService.addUser(any(User.class), any(OAuth2Authentication.class)))
                .thenReturn(UserServiceTestUtils.getUser());

        ResponseEntity<UserResDTO> userResDTOResponseEntity = userController.donorSignUp(getUserAddDto(UserRole.DONOR), new OAuth2Authentication(null, UserServiceTestUtils.getUserAuthentication()));
        assertNotNull(userResDTOResponseEntity);
        assertEquals(HttpStatus.CREATED, userResDTOResponseEntity.getStatusCode());
        UserResDTO userResDTO = userResDTOResponseEntity.getBody();

        assertNotNull(userResDTO);
        assertEquals(1, userResDTO.getId());
        assertEquals(UserServiceTestUtils.USERNAME, userResDTO.getEmail());
    }

    private static UserReqDTO getUserAddDto(UserRole userRole) {
        UserReqDTO userReqDTO = new UserReqDTO();

        userReqDTO.setUserName(UserServiceTestUtils.USERNAME);
        userReqDTO.setEmail(UserServiceTestUtils.USERNAME);
        userReqDTO.setGender("MALE");
        userReqDTO.setFName("Manjunath");
        userReqDTO.setLName("Asundi");
        userReqDTO.setPhone("9886988915");
        userReqDTO.setRole(userRole);

        return userReqDTO;
    }


    @Test
    void innovatorSignUp() throws CustomException, ResourceNotFoundException {
        when(userService.addUser(any(User.class), any(OAuth2Authentication.class)))
                .thenReturn(UserServiceTestUtils.getUser());

        ResponseEntity<UserResDTO> userResDTOResponseEntity = userController.innovatorSignUp(getUserAddDto(UserRole.INNOVATOR), new OAuth2Authentication(null, UserServiceTestUtils.getUserAuthentication()));
        assertNotNull(userResDTOResponseEntity);
        assertEquals(HttpStatus.CREATED, userResDTOResponseEntity.getStatusCode());
        UserResDTO userResDTO = userResDTOResponseEntity.getBody();

        assertNotNull(userResDTO);
        assertEquals(1, userResDTO.getId());
        assertEquals(UserServiceTestUtils.USERNAME, userResDTO.getEmail());
    }
}