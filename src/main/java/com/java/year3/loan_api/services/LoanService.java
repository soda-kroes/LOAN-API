package com.java.year3.loan_api.services;


import com.java.year3.loan_api.dto.request.LoanRequestDTO;
import com.java.year3.loan_api.entity.Loan;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public interface LoanService {
    Loan createLoan(LoanRequestDTO loanRequestDTO) throws Exception;
    List<Loan> getLoans() throws Exception;
    Loan getLoanByNationalityId(String nationalityId) throws Exception;
    Loan getLoanById(Long loanId) throws Exception;
    Loan updateLoan(Long loanId,LoanRequestDTO loanRequestDTO) throws Exception;
    Boolean deleteLoanById(Long loanId) throws Exception;
    Page<Loan> getAllLoans(Map<String,String> params) throws Exception;
    void writeCustomerToCsv(List<Loan> loans, Writer writer) throws IOException;
}
