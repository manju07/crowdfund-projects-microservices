package com.crowdfund.projects.microservices.userservice.service.impl;

import com.crowdfund.projects.microservices.common.code.constant.UserRole;
import com.crowdfund.projects.microservices.common.code.entity.Role;
import com.crowdfund.projects.microservices.common.code.entity.User;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.userservice.UserServiceTestUtils;
import com.crowdfund.projects.microservices.userservice.repository.RoleRepository;
import com.crowdfund.projects.microservices.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;


    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void addUserTest() throws CustomException, ResourceNotFoundException {
        User user = UserServiceTestUtils.getUser();

        when(userRepository.findByEmail(UserServiceTestUtils.USERNAME))
                .thenReturn(Optional.of(user))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.empty());

        when(userRepository.findByPhone(any()))
                .thenReturn(Optional.of(user))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.empty());

        Role donorRole = new Role();
        donorRole.setName(UserRole.DONOR);
        when(roleRepository.findByName(any()))
                .thenReturn(null)
                .thenReturn(donorRole);

        when(passwordEncoder.encode(any()))
                .thenReturn("42342dsf");

        when(userRepository.save(any())).thenReturn(UserServiceTestUtils.getUser());

        CustomException customException = assertThrows(CustomException.class, () -> userService.addUser(user, new OAuth2Authentication(null, UserServiceTestUtils.getUserAuthentication())));

        assertNotNull(customException);
        assertEquals("Email already exist", customException.getDescription());
        assertEquals(HttpStatus.CONFLICT, customException.getStatus());

        customException = assertThrows(CustomException.class, () -> userService.addUser(user, new OAuth2Authentication(null, UserServiceTestUtils.getUserAuthentication())));

        assertNotNull(customException);
        assertEquals("Phone number already exist", customException.getDescription());
        assertEquals(HttpStatus.CONFLICT, customException.getStatus());

        user.setRole(donorRole);
        customException = assertThrows(CustomException.class, () -> userService.addUser(user, new OAuth2Authentication(null, UserServiceTestUtils.getUserAuthentication())));

        assertNotNull(customException);
        assertEquals("Role doesn't exist", customException.getDescription());
        assertEquals(HttpStatus.BAD_REQUEST, customException.getStatus());

        User savedUser = userService.addUser(user, new OAuth2Authentication(null, UserServiceTestUtils.getUserAuthentication()));
        assertNotNull(savedUser);
        assertEquals(1, savedUser.getId());
        assertEquals(UserServiceTestUtils.USERNAME, savedUser.getEmail());

    }

}