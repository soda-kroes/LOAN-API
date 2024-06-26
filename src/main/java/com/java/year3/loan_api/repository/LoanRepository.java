package com.java.year3.loan_api.repository;

import com.java.year3.loan_api.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {
    boolean existsByNationalityId(String nationalityId);
    Optional<Loan> findByNationalityId(String nationalityId);
}
