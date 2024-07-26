package com.crowdfund.projects.microservices.projectservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xmlpull.v1.XmlPullParserException;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SwaggerConfigTest {

    @InjectMocks
    private SwaggerConfig swaggerConfig;

    @Test
    void apiTest() throws XmlPullParserException, IOException {
        Docket docket = swaggerConfig.api();
        assertNotNull(docket);
    }
}