package com.java.year3.loan_api.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LoanResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String maritalStatus;
    private String gender;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private String nationalityId;
    private byte[] nationalityImage;
    private byte[] selfieImage;
    private String address;
    private BigDecimal loanAmount;
    private double monthlyPayment;
    private Integer loanTerm;
    private String currency;
    private double totalOfPayment;
    private double totalInterest;
    private String createdDate;
    private String updatedDate;
    private String branch;
    private String loanType;
}
