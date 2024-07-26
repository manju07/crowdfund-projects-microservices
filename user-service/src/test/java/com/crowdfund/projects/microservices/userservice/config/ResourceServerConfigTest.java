package com.crowdfund.projects.microservices.userservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ResourceServerConfigTest {

    @InjectMocks
    private ResourceServerConfig resourceServerConfig;

    @Test
    void configure() throws Exception {
        resourceServerConfig.configure(new HttpSecurity(mock(ObjectPostProcessor.class), mock(AuthenticationManagerBuilder.class), new HashMap<>()));
    }
}