package com.java.year3.loan_api.services;


import com.java.year3.loan_api.dto.request.LoanRequestDTO;
import com.java.year3.loan_api.dto.response.LoanResponseDTO;
import com.java.year3.loan_api.entity.Loan;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public interface LoanService {
    LoanResponseDTO createLoan(LoanRequestDTO loanRequestDTO) throws Exception;
    List<LoanResponseDTO> getLoans() throws Exception;
    LoanResponseDTO getLoanByNationalityId(String nationalityId) throws Exception;
    LoanResponseDTO getLoanById(Long loanId) throws Exception;
    LoanResponseDTO updateLoan(Long loanId,LoanRequestDTO loanRequestDTO) throws Exception;
    Boolean deleteLoanById(Long loanId) throws Exception;
    Page<LoanResponseDTO> getAllLoans(Map<String,String> params) throws Exception;
    void writeCustomerToCsv(List<LoanResponseDTO> loans, Writer writer) throws IOException;
}
