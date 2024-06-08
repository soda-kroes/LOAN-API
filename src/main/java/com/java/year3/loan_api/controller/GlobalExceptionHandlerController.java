package com.java.year3.loan_api.controller;


import com.java.year3.loan_api.dto.response.StatusResponseDTO;
import com.java.year3.loan_api.exception.AlreadyExistException;
import com.java.year3.loan_api.exception.BadRequestException;
import com.java.year3.loan_api.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


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
        response.setErrorCode(403);
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

}