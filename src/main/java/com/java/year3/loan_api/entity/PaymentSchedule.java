package com.java.year3.loan_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Payment_schedule")
public class PaymentSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monthly_payment_amount", nullable = false)
    private BigDecimal monthlyPaymentAmount;

    @Column(name = "payment_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "is_payment", nullable = false, columnDefinition = "boolean default false")
    private boolean isPayment;

    @Column(name = "status",length = 100)
    private String status;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;
}
