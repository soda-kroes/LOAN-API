package com.java.year3.loan_api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PaymentRequestDTO {
    @NotNull(message = "The payment date field is required.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("payment_date")
    private LocalDate paymentDate;

    @NotNull(message = "The payment amount field is required.")
    @JsonProperty("payment_amount")
    private BigDecimal paymentAmount;

    @NotNull(message = "The payment description field is required.")
    @JsonProperty("payment_description")
    private String paymentDescription;

    @NotNull(message = "The payment method Id field is required.")
    @JsonProperty("payment_method_id")
    private Long paymentMethodId;

    @NotNull(message = "The loan Id field is required.")
    @JsonProperty("loan_id")
    private Long loanId;

}
