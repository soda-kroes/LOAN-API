package com.java.year3.loan_api.services.impl;

import com.java.year3.loan_api.dto.request.LoanTypeRequestDTO;
import com.java.year3.loan_api.entity.LoanType;
import com.java.year3.loan_api.exception.NotFoundException;
import com.java.year3.loan_api.repository.LoanTypeRepository;
import com.java.year3.loan_api.services.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanTypeServiceImpl implements LoanTypeService {
    private final LoanTypeRepository loanTypeRepository;

    @Override
    public LoanType addLoanType(LoanTypeRequestDTO loanTypeRequest) throws Exception {
        LoanType loanType = new LoanType();
        loanType.setName(loanTypeRequest.getName());
        try {
            return loanTypeRepository.save(loanType);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    @Override
    public List<LoanType> getAllLoanTypes() {
        return loanTypeRepository.findAll();
    }

    @Override
    public LoanType getLoanTypeById(Long id) throws Exception {
        return loanTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Loan type with id " + id + " not found"));
    }

    @Override
    public Double getInterestRateById(Long id) throws Exception {
        return loanTypeRepository.findInterestRateById(id).orElseThrow(()-> new NotFoundException("Interest rate with id " + id + " not found"));
    }

}
