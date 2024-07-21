package com.crowdfund.projects.microservices.common.code.constant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * User Role enum
 *
 * @author Manjunath Asundi
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum UserRole {
    ADMIN, INNOVATOR, DONOR
}