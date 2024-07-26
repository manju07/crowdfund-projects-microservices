package com.crowdfund.projects.microservices.oauth2authenticationserver.service;

import com.crowdfund.projects.microservices.common.code.entity.User;
import com.crowdfund.projects.microservices.oauth2authenticationserver.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    private static String USERNAME = "manjunath@gmail.com";
    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Mock
    private UsersRepository usersRepository;

    @Test
    void loadUserByUsernameTest() {
        when(usersRepository.findByUserName(USERNAME))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(getUser()));

        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(USERNAME));

        assertNotNull(usernameNotFoundException);
        assertEquals("Username not found!", usernameNotFoundException.getMessage());

        UserDetails userDetails = userDetailsService.loadUserByUsername(USERNAME);

        assertNotNull(userDetails);
        assertEquals(USERNAME, userDetails.getUsername());
    }

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName(USERNAME);
        user.setEmail(USERNAME);
        user.setIsEnabled(true);
        user.setGender("MALE");
        user.setFName("Manjunath");
        user.setLName("Asundi");
        user.setPhone("9886988915");
        return user;
    }
}