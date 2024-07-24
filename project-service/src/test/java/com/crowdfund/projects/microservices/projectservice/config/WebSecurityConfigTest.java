//package com.crowdfund.projects.microservices.projectservice.config;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.config.annotation.ObjectPostProcessor;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
//class WebSecurityConfigTest {
//
//    @InjectMocks
//    private WebSecurityConfig webSecurityConfig;
//
//    @Test
//    void configureTest() throws Exception {
//        WebSecurity webSecurity = mock(WebSecurity.class);
//
//        WebSecurity.IgnoredRequestConfigurer ignoredRequestConfigurer = mock(WebSecurity.IgnoredRequestConfigurer.class);
//        when(webSecurity.ignoring()).thenReturn(ignoredRequestConfigurer);
//
//        webSecurityConfig.configure(webSecurity);
//    }
//
//}