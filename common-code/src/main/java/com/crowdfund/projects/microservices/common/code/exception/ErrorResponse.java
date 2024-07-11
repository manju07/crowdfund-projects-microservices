package com.crowdfund.projects.microservices.common.code.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;


/**
 * The type Error response.
 * 
 * @author Manjunath Asundi
 */

@Data
@AllArgsConstructor
public class ErrorResponse {

    private Timestamp timestamp;
    private int status;
    private String message;
    private String description;
}