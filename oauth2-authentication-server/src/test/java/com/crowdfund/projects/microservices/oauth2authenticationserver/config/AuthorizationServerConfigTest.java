package com.crowdfund.projects.microservices.oauth2authenticationserver.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServerConfigTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private DataSource dataSource;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthorizationServerConfig authorizationServerConfig;

    @Test
    void jdbcTokenStore() {
        TokenStore tokenStore = authorizationServerConfig.jdbcTokenStore();
        assertNotNull(tokenStore);
    }

    @Test
    void configureClientDetailsServiceConfigureTest() throws Exception {
        ClientDetailsServiceConfigurer clientDetailsServiceConfigurer = mock(ClientDetailsServiceConfigurer.class);
        when(clientDetailsServiceConfigurer.jdbc(any()))
                .thenReturn(new JdbcClientDetailsServiceBuilder());

        authorizationServerConfig.configure(clientDetailsServiceConfigurer);

        verify(clientDetailsServiceConfigurer).jdbc(any());
    }

    @Test
    void configureAuthorizationServerEndpointsTest() throws Exception {
        authorizationServerConfig.configure(new AuthorizationServerEndpointsConfigurer());
    }

    @Test
    void configureAuthorizationServerSecurityTest() throws Exception {
        authorizationServerConfig.configure(new AuthorizationServerSecurityConfigurer());
    }
}