package com.java.year3.loan_api.services.impl;

import com.java.year3.loan_api.entity.Branch;
import com.java.year3.loan_api.exception.NotFoundException;
import com.java.year3.loan_api.repository.BranchRepository;
import com.java.year3.loan_api.services.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;

    @Override
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    @Override
    public Branch getBranchByBranchCode(String branchCode) throws NotFoundException {
        return branchRepository.findByCode(branchCode).orElseThrow(()-> new NotFoundException("Branch with id "+branchCode+" not found"));
    }

}
