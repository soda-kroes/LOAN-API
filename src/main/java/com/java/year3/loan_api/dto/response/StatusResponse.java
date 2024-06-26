package com.java.year3.loan_api.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class StatusResponse {
    private Integer errorCode;
    private String errorMessage;
    private Map<String, String> errors;

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
