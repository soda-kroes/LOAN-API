package com.java.year3.loan_api.repository;

import com.java.year3.loan_api.entity.PaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentScheduleRepository extends JpaRepository<PaymentSchedule, Long> {
    List<PaymentSchedule> findByLoanIdAndPaymentDate(Long loanId, LocalDate paymentDate);
}
