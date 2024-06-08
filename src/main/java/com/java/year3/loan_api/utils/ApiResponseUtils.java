package com.java.year3.loan_api.utils;

import lombok.Data;

@Data
public class ApiResponseUtils {
    public static final String USER_EXISTS_CODE="403";
    public static final String USER_EXISTS_MESSAGE="Email Not Available.";

    public static final String SUCCESS_CODE="200";
    public static final String SUCCESS_MESSAGE="success";

    public static final String FORBIDDEN_CODE="403";
    public static final String FORBIDDEN_MESSAGE="Invalid credentials.";
}
