package com.crowdfund.projects.microservices.zuulapigatewayserver.config;

import com.netflix.zuul.exception.ZuulException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ZuulLoggingFilterTest {

    @InjectMocks
    private ZuulLoggingFilter zuulLoggingFilter;

    @Test
    void runTest() throws ZuulException {
//        Object run = zuulLoggingFilter.run();
//        assertNull(run);
    }

    @Test
    void shouldFilter() {
        assertFalse(zuulLoggingFilter.shouldFilter());
    }

    @Test
    void filterOrder() {
        assertEquals(0, zuulLoggingFilter.filterOrder());
    }

    @Test
    void filterType() {
        assertEquals("pre", zuulLoggingFilter.filterType());
    }
}