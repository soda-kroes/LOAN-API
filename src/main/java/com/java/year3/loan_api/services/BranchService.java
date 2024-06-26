package com.java.year3.loan_api.services;

import com.java.year3.loan_api.entity.Branch;
import com.java.year3.loan_api.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface BranchService {
    List<Branch> getAllBranches();
    Branch getBranchByBranchCode(String branchCode) throws NotFoundException;
}
