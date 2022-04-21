package com.bartczak.zai.lodging.common;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { InvalidRequestException.class })
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(RuntimeException ex, WebRequest request) {
        val body = new ErrorResponse(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
