package com.aitbekov.gym.handler;

import com.aitbekov.gym.exceptions.AuthenticationException;
import com.aitbekov.gym.exceptions.ExceptionNotFoundResponse;
import com.aitbekov.gym.exceptions.NotFoundException;
import com.aitbekov.gym.exceptions.RuntimeExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionNotFoundResponse> handleExceptionNotFound(Exception e) {

        ExceptionNotFoundResponse exceptionResponse = new ExceptionNotFoundResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not found exception",
                e.getMessage(),
                e.getMessage().split(" ")[0]
        );
        logger.error("{}", e.getCause());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RuntimeExceptionResponse> handleRuntimeException(RuntimeException e) {
        RuntimeExceptionResponse errors = new RuntimeExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Authorization exception",
                e.getMessage(),
                LocalDateTime.now()
        );
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
