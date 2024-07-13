package com.crowdfund.projects.microservices.projectservice.exception;

import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ErrorResponse;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;

/**
 * The type Global exception handler.
 *
 * @author Manjunath Asundi
 */
@ControllerAdvice
@Slf4j
public class ProjectServiceGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Resource not found exception response entity.
     *
     * @param ex      type of exception
     * @param request WebRequest
     * @return the response entity
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.error("resourceNotFoundException " + ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Globle excpetion handler response entity.
     *
     * @param exception type of exception
     * @param request   WebRequest
     * @return the response entity
     */
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> customExceptionHandler(CustomException exception, WebRequest request) {
        log.error("customExceptionHandler", exception.getMessage(), exception);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), exception.getStatus().value(), exception.getMessage(), exception.getDescription());
        return new ResponseEntity<>(errorDetails, exception.getStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> globalExceptionHandler(Exception exception, WebRequest request) {
        log.error("globalExceptionHandler", exception.getMessage(), exception);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        log.error("handleAsyncRequestTimeoutException", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleBindException", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleConversionNotSupported", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleExceptionInternal", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpMediaTypeNotAcceptable", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpMediaTypeNotSupported", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpMessageNotReadable", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpMessageNotWritable", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpRequestMethodNotSupported", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleMethodArgumentNotValid", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleMissingPathVariable", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleMissingServletRequestParameter", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleMissingServletRequestPart", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleNoHandlerFoundException", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleServletRequestBindingException", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleTypeMismatch", ex.getMessage(), ex);
        ErrorResponse errorDetails = new ErrorResponse(new Timestamp(System.currentTimeMillis()), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }
}