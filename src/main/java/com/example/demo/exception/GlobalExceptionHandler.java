package com.example.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger	= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDTO> generateExceptionResponse(ResponseStatusException ex) {
        logger.warn("ResponseStatusException is thrown", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setStatus(String.valueOf(ex.getStatus().value()));
        errorDTO.setTime(new Date().toString());

        return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
    }
}
