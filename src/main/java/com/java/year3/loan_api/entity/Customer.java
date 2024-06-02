package com.java.year3.loan_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "marital_status", nullable = false, length = 50)
    private String maritalStatus;

    @Column(name = "gender", nullable = false, length = 15)
    private String Gender;

    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "nationality_id", nullable = false, length = 10)
    private Integer nationalityId;

    @Column(name = "nationality_image", nullable = false, length = 255)
    private String nationalityImage;

    @Column(name = "selfie_image", nullable = false, length = 255)
    private String selfieImage;

    @Column(name = "province_code", nullable = false)
    private Integer provinceCode;

    @Column(name = "district_code", nullable = false)
    private Integer districtCode;

    @Column(name = "commune_code", nullable = false)
    private Integer communeCode;

    @Column(name = "village_code", nullable = false)
    private Integer villageCode;

    @Column(name = "loan_amount", nullable = false)
    private BigDecimal loanAmount;

    @Column(name = "loan_type", nullable = false)
    private String loanType;

    @Column(name = "loan_term", nullable = false)
    private Integer loanTerm;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "branch_code", nullable = false)
    private String branchCode;

    @ManyToOne
    @JoinColumn(name = "loan_officer_id")
    private LoanOfficer loanOfficer;

}
