package com.sleepkqq.taskmanagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static com.sleepkqq.taskmanagement.constants.ExceptionResponseProperties.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception ex, HttpServletRequest request) {
        HttpStatus status;
        var errorResponse = new HashMap<>();

        switch (ex) {
            case MethodArgumentNotValidException e -> {
                status = HttpStatus.BAD_REQUEST;
                var errors = new HashMap<>();
                e.getBindingResult().getAllErrors().forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
                errorResponse.put(ERRORS, errors);
            }
            case MethodArgumentTypeMismatchException e -> {
                status = HttpStatus.BAD_REQUEST;
                errorResponse.put(MESSAGE, e.getMessage());
            }
            case UsernameNotFoundException e -> {
                status = HttpStatus.NOT_FOUND;
                errorResponse.put(MESSAGE, e.getMessage());
            }
            case NoSuchElementException e -> {
                status = HttpStatus.NOT_FOUND;
                errorResponse.put(MESSAGE, e.getMessage());
            }
            default -> {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                errorResponse.put(MESSAGE, ex.getMessage());
            }
        }

        errorResponse.put(TIMESTAMP, LocalDateTime.now());
        errorResponse.put(STATUS, status.value());
        errorResponse.put(ERROR, status.getReasonPhrase());
        errorResponse.put(PATH, request.getRequestURI());

        log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

}