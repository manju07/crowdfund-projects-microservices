//package com.crowdfund.projects.microservices.userservice.config;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.platform.commons.util.ReflectionUtils;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.config.annotation.ObjectPostProcessor;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//
//@ExtendWith(MockitoExtension.class)
//class WebSecurityConfigTest {
//
//    @InjectMocks
//    private WebSecurityConfig webSecurityConfig;
//
//    @Test
//    void configure() throws Exception {
//        webSecurityConfig.configure(new WebSecurity(mock(ObjectPostProcessor.class)));
//    }
//}