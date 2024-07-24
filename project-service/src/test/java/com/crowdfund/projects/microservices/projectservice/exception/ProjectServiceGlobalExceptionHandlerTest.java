package com.crowdfund.projects.microservices.projectservice.exception;

import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProjectServiceGlobalExceptionHandlerTest {
    @InjectMocks
    private ProjectServiceGlobalExceptionHandler projectServiceGlobalExceptionHandler;

    @Test
    void resourceNotFoundException() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.resourceNotFoundException(new ResourceNotFoundException("Not Found"), null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.NOT_FOUND, objectResponseEntity.getStatusCode());
    }

    @Test
    void customExceptionHandler() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.customExceptionHandler(new CustomException("Internal server error"), null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, objectResponseEntity.getStatusCode());
    }

    @Test
    void globalExceptionHandler() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.globalExceptionHandler(new Exception("Internal server error"), null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleAsyncRequestTimeoutException() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleAsyncRequestTimeoutException(new AsyncRequestTimeoutException(), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleBindException() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleBindException(mock(BindException.class), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleConversionNotSupported() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleConversionNotSupported(mock(ConversionNotSupportedException.class), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleExceptionInternal() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleExceptionInternal(new Exception("Internal Server error"), null, null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleHttpMediaTypeNotAcceptable() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleHttpMediaTypeNotAcceptable(new HttpMediaTypeNotAcceptableException("Internal Server error"), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleHttpMediaTypeNotSupported() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleHttpMediaTypeNotSupported(new HttpMediaTypeNotSupportedException("Internal Server error"), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleHttpMessageNotReadable() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleHttpMessageNotReadable(new HttpMessageNotReadableException("Internal Server error"), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleHttpMessageNotWritable() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleHttpMessageNotWritable(new HttpMessageNotWritableException("Internal Server error"), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleHttpRequestMethodNotSupported() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleHttpRequestMethodNotSupported(new HttpRequestMethodNotSupportedException("Internal Server error"), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleMethodArgumentNotValid() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleMethodArgumentNotValid(mock(MethodArgumentNotValidException.class), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleMissingPathVariable() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleMissingPathVariable(mock(MissingPathVariableException.class), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleMissingServletRequestParameter() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleMissingServletRequestParameter(mock(MissingServletRequestParameterException.class), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleMissingServletRequestPart() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleMissingServletRequestPart(mock(MissingServletRequestPartException.class), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleNoHandlerFoundException() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleNoHandlerFoundException(mock(NoHandlerFoundException.class), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleServletRequestBindingException() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleServletRequestBindingException(mock(ServletRequestBindingException.class), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void handleTypeMismatch() {
        ResponseEntity<Object> objectResponseEntity = projectServiceGlobalExceptionHandler.handleTypeMismatch(mock(TypeMismatchException.class), null, HttpStatus.BAD_REQUEST, null);
        assertNotNull(objectResponseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }
}