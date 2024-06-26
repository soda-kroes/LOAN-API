package com.java.year3.loan_api.services.impl;

import com.java.year3.loan_api.dto.response.PaymentScheduleResponseDTO;
import com.java.year3.loan_api.entity.Loan;
import com.java.year3.loan_api.entity.PaymentSchedule;
import com.java.year3.loan_api.repository.PaymentScheduleRepository;
import com.java.year3.loan_api.services.LoanService;
import com.java.year3.loan_api.services.PaymentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentScheduleServiceImpl implements PaymentScheduleService {
    private final PaymentScheduleRepository paymentScheduleRepository;
    private final LoanService loanService;

    public List<PaymentScheduleResponseDTO> getPaymentByLoanIdAndPaymentDate(Long loanId, LocalDate paymentDate) throws Exception {
        Loan loanById = loanService.getLoanById(loanId);
        List<PaymentSchedule> data = paymentScheduleRepository.findByLoanIdAndPaymentDate(loanById.getId(), paymentDate);
        List<PaymentScheduleResponseDTO> responseDTOs = new ArrayList<>();

        for (PaymentSchedule paymentSchedule : data) {
            PaymentScheduleResponseDTO paymentScheduleResponseDTO = new PaymentScheduleResponseDTO();
            paymentScheduleResponseDTO.setDescription(paymentSchedule.getDescription());
            paymentScheduleResponseDTO.setPaymentDate(paymentSchedule.getPaymentDate());
            paymentScheduleResponseDTO.setLoanId(paymentSchedule.getId());
            paymentScheduleResponseDTO.setPayment(paymentSchedule.isPayment());
            paymentScheduleResponseDTO.setMonthlyPaymentAmount(paymentSchedule.getMonthlyPaymentAmount());
            responseDTOs.add(paymentScheduleResponseDTO);
        }

        return responseDTOs;
    }
}
