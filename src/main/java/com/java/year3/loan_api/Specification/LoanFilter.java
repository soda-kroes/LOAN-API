package com.java.year3.loan_api.Specification;

import lombok.Data;

@Data
public class LoanFilter {
    private Long requestNo;
    private Long id;
    private Integer nationalityId;
    private String loanType;
}
