package com.java.year3.loan_api.repository;

import com.java.year3.loan_api.entity.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {
    @Query(value = "SELECT interest_rate FROM loan_type WHERE id = :id", nativeQuery = true)
    Optional<Double> findInterestRateById(@Param("id") Long id);
}