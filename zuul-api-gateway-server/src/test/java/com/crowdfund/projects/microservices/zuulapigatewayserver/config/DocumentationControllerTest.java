package com.crowdfund.projects.microservices.zuulapigatewayserver.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DocumentationControllerTest {

    @InjectMocks
    private DocumentationController documentationController;

    @Test
    void getTest() {
        List<SwaggerResource> swaggerResources = documentationController.get();
        assertNotNull(swaggerResources);
        assertEquals(2, swaggerResources.size());
    }
}