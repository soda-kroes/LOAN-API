package com.java.year3.loan_api.services;

import com.java.year3.loan_api.dto.request.LoanTypeRequestDTO;
import com.java.year3.loan_api.entity.LoanType;

import java.util.List;

public interface LoanTypeService {
    LoanType addLoanType(LoanTypeRequestDTO loanTypeRequest) throws Exception;
    List<LoanType> getAllLoanTypes();
    LoanType getLoanTypeById(Long id) throws Exception;
    Double getInterestRateById(Long id) throws Exception;

}
