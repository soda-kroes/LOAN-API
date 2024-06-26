package com.java.year3.loan_api.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
    private LocalDate paymentDate;
    private BigDecimal paymentAmount;
    private String paymentDescription;
    private Long paymentMethodId;
    private Long loanId;
    private LocalDateTime createdPaymentDate;
}
