package com.crowdfund.projects.microservices.oauth2authenticationserver.config;

import com.crowdfund.projects.microservices.oauth2authenticationserver.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebSecurityConfigTest {

    @Mock
    private CustomUserDetailsService userDetailsService;

    @InjectMocks
    private WebSecurityConfig webSecurityConfig;

//    @Test
//    void getAuthenticationManager() throws Exception {
//        AuthenticationManager authenticationManager = webSecurityConfig.getAuthenticationManager();
//        assertNotNull(authenticationManager);
//    }

    @Test
    void passwordEncoder() {
        PasswordEncoder passwordEncoder = webSecurityConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
    }

    @Test
    void configureHttpSecurityTest() throws Exception {
        webSecurityConfig.configure(new HttpSecurity(mock(ObjectPostProcessor.class), mock(AuthenticationManagerBuilder.class), new HashMap<>()));
    }

    @Test
    void configureAuthenticationManagerBuilderTest() throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = mock(AuthenticationManagerBuilder.class);

        when(authenticationManagerBuilder.userDetailsService(any()))
                .thenReturn(new DaoAuthenticationConfigurer<>(userDetailsService));

        webSecurityConfig.configure(authenticationManagerBuilder);
    }
}