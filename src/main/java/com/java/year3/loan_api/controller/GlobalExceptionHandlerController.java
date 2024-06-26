package com.java.year3.loan_api.controller;


import com.java.year3.loan_api.dto.response.StatusResponse;
import com.java.year3.loan_api.dto.response.StatusResponseDTO;
import com.java.year3.loan_api.exception.AlreadyExistException;
import com.java.year3.loan_api.exception.BadRequestException;
import com.java.year3.loan_api.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        StatusResponseDTO response = new StatusResponseDTO();
        response.setErrorCode(500);
        response.setErrorMessage(ex.getMessage());
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> handleAlreadyExistException(AlreadyExistException ex) {
        StatusResponseDTO response = new StatusResponseDTO();
        response.setErrorCode(403);
        response.setErrorMessage(ex.getMessage());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        StatusResponseDTO response = new StatusResponseDTO();
        response.setErrorCode(404);
        response.setErrorMessage(ex.getMessage());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        StatusResponseDTO response = new StatusResponseDTO();
        response.setErrorCode(400);
        response.setErrorMessage(ex.getMessage());
        return ResponseEntity.status(400).body(response);
    }


        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });

            StatusResponse response = new StatusResponse();
            response.setErrorCode(400);
            response.setErrorMessage("Validation failed");
            response.setErrors(errors);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


}