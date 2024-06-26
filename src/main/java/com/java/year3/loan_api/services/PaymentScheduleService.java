package com.java.year3.loan_api.services;

import com.java.year3.loan_api.dto.response.PaymentScheduleResponseDTO;
import com.java.year3.loan_api.entity.PaymentSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentScheduleService {
    List<PaymentScheduleResponseDTO> getPaymentByLoanIdAndPaymentDate(Long loanId, LocalDate paymentDate) throws Exception;
}
