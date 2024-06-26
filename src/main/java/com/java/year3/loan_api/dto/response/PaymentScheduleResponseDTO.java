package com.java.year3.loan_api.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentScheduleResponseDTO {
    private BigDecimal monthlyPaymentAmount;
    private LocalDate paymentDate;
    private String description;
    private boolean isPayment;
    private Long loanId;
}
