package com.java.year3.loan_api.services;

import com.java.year3.loan_api.dto.response.PaymentScheduleResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface PaymentScheduleService {
    List<PaymentScheduleResponseDTO> getPaymentByLoanIdAndPaymentDate(Long loanId, LocalDate paymentDate) throws Exception;
}
