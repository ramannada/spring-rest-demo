package com.ramannada.springdemo.controller.handler;

import com.ramannada.springdemo.utils.exception.ResourceNotFoundException;
import com.ramannada.springdemo.entity.ErrorDetail;
import com.ramannada.springdemo.entity.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundExcetion(ResourceNotFoundException exception,
                                                            HttpServletRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource not found");
        errorDetail.setDevMessage(exception.getClass().getName());
        errorDetail.setDetail(exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetail);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Message Not Readable");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDevMessage(ex.getClass().getName());

        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Validation vailed");
        errorDetail.setDevMessage(ex.getClass().getName());
        errorDetail.setDetail(ex.getMessage());

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError fe:fieldErrors) {
            List<ValidationError> validationErrors = errorDetail.getErrors().get(fe.getField());

            if (validationErrors == null) {
                validationErrors = new ArrayList<ValidationError>();
                errorDetail.getErrors().put(fe.getField(), validationErrors);
            }
            ValidationError validationError = new ValidationError();
            validationError.setCode(fe.getCode());
            validationError.setMessage(fe.getDefaultMessage());
            validationErrors.add(validationError);

        }

        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }
}
