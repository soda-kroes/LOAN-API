package com.java.year3.loan_api.dto.response;


import lombok.Data;

@Data
public class StatusResponseDTO {
    private Integer errorCode;
    private String errorMessage;
}