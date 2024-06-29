package com.java.year3.loan_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "loan")
@Data
public class Loan {
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
    private String gender;

    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "nationality_id", nullable = false, length = 10)
    private String nationalityId;

    @Column(name = "nationality_image", nullable = false)
    @Lob
    private byte[] nationalityImage;

    @Column(name = "selfie_image", nullable = false)
    @Lob
    private byte[] selfieImage;

    @Column(name = "address", nullable = false,length = 250)
    private String address;

    @Column(name = "loan_amount", nullable = false)
    private BigDecimal loanAmount;

    @Column(name = "monthly_payment",nullable = false)
    private double monthlyPayment;


    @Column(name = "loan_term", nullable = false)
    private Integer loanTerm;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "total_of_payment", nullable = false)
    private double totalOfPayment;

    @Column(name = "total_interest", nullable = false)
    private double totalInterest;

    @Column(name = "created_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "branch_code")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "loan_type_id")
    private LoanType loanType;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<PaymentSchedule> paymentSchedule;

}
