package com.java.year3.loan_api.exception;

public class BadRequestException extends Exception{
    public BadRequestException(String message){
        super(message);
    }
}
