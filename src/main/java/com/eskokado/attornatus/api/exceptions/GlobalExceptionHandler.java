package com.eskokado.attornatus.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new ErrorResponse(errorMessage), HttpStatus.NOT_FOUND);
    }
}
