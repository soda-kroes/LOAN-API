package com.java.year3.loan_api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.year3.loan_api.utils.ValidAge;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LoanRequestDTO {

    @NotNull(message = "The first name field is required.")
    @JsonProperty("first_name")
    private String firstName;

    @NotNull(message = "The last name field is required.")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "The marital status field is required.")
    @JsonProperty("marital_status")
    private String maritalStatus;

    @ValidAge(message = "You must be between 18 and 65 years old.")
    @NotNull(message = "The date of birth field is required.")
    @JsonProperty("date_of_birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Size(min = 9, max = 11, message = "The fields is required min 9 digit and max 11 digits.")
    @NotNull(message = "The phone number field is required.")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotNull(message = "The gender field is required.")
    @JsonProperty("gender")
    private String gender;

    @Email(message = "The email address is invalid.")
    @NotNull(message = "The email field is required.")
    @JsonProperty("email")
    private String email;

    @Size(min = 9, max = 9, message = "The fields is required 9 digit.")
    @NotNull(message = "The nationality id field is required.")
    @JsonProperty("nationality_id")
    private String nationalityId;

    @NotNull(message = "The nationality image field is required.")
    @JsonProperty("nationality_image")
    private String nationalityImage;

    @NotNull(message = "The selfie image field is required.")
    @JsonProperty("selfie_image")
    private String selfieImage;

    @NotNull(message = "The address field is required.")
    @JsonProperty("address")
    private String address;

    @NotNull(message = "The loan amount field is required.")
    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @NotNull(message = "The loan type id field is required.")
    @JsonProperty("loan_type_id")
    private Long loanTypeId;

    @NotNull(message = "The loan term field is required.")
    @JsonProperty("loan_term")
    private Integer loanTerm;

    @NotNull(message = "The currency field is required.")
    @JsonProperty("currency")
    private String currency;

    @NotNull(message = "The total of payment field is required.")
    @JsonProperty("total_of_payment")
    private double totalOfPayment;

    @NotNull(message = "The total interest field is required.")
    @JsonProperty("total_interest")
    private double totalInterest;

//    @NotNull(message = "The monthly payment field is required.")
    @JsonProperty("monthly_payment")
    private BigDecimal monthlyPayment;

    @NotNull(message = "The created date field is required.")
    @JsonProperty("created_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonProperty("updated_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;

    @NotNull(message = "The branch code field is required.")
    @JsonProperty("branch_code")
    private String branchCode;
}
