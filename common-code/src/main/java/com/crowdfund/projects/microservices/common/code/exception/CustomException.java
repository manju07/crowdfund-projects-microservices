package com.crowdfund.projects.microservices.common.code.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

/**
 * @author Manjunath Asundi
 */
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomException extends Exception {

    private static final long serialVersionUID = 7108160092721922439L;

    private Timestamp timestamp;
    private HttpStatus status;
    private String message;
    private String description;

    public CustomException(String description) {
        super(description);
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.message = description;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.description = description;
    }

    public CustomException(String message, HttpStatus status, String description) {
        super(description);
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.status = status;
        this.message = message;
        this.description = description;
    }
}