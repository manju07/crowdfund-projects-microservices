package com.crowdfund.projects.microservices.oauth2authenticationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author Manjunath Asundi
 */
@SpringBootApplication
@EntityScan(basePackages = {"com.crowdfund.projects.microservices.common.code.entity"})
public class Oauth2AuthenticationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2AuthenticationServerApplication.class, args);
    }
}