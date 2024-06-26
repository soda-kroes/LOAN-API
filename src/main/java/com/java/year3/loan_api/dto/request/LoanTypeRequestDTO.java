package com.java.year3.loan_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanTypeRequestDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("interst_rate")
    private BigDecimal interestRate;
}
