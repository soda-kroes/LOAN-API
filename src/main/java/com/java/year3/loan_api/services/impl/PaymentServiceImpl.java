package com.java.year3.loan_api.services.impl;

import com.java.year3.loan_api.dto.request.PaymentRequestDTO;
import com.java.year3.loan_api.dto.response.PaymentResponseDTO;
import com.java.year3.loan_api.dto.response.PaymentScheduleResponseDTO;
import com.java.year3.loan_api.entity.Loan;
import com.java.year3.loan_api.entity.Payment;
import com.java.year3.loan_api.entity.PaymentMethod;
import com.java.year3.loan_api.entity.PaymentSchedule;
import com.java.year3.loan_api.repository.PaymentRepository;
import com.java.year3.loan_api.repository.PaymentScheduleRepository;
import com.java.year3.loan_api.services.LoanService;
import com.java.year3.loan_api.services.PaymentMethodService;
import com.java.year3.loan_api.services.PaymentScheduleService;
import com.java.year3.loan_api.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.compress.harmony.pack200.PackingUtils.log;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMethodService paymentMethodService;
    private final LoanService loanService;
    private final PaymentScheduleService paymentScheduleService;
    private final PaymentScheduleRepository paymentScheduleRepository;


    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequest) throws Exception {
        Loan loanById = loanService.getLoanById(paymentRequest.getLoanId());
        Payment payment = new Payment();
        payment.setCreatedPaymentDate(LocalDateTime.now());
        payment.setPaymentDate(paymentRequest.getPaymentDate());
        payment.setPaymentAmount(paymentRequest.getPaymentAmount());
        payment.setPaymentDescription(paymentRequest.getPaymentDescription());
        PaymentMethod paymentMethodById = paymentMethodService.getPaymentMethodById(paymentRequest.getPaymentMethodId());
        payment.setPaymentMethod(paymentMethodById);
        payment.setLoan(loanById);

        System.out.println("==========================================");
        System.out.println(paymentRequest.getPaymentDate());
        System.out.println(loanById.getId());
        System.out.println("==========================================");

        List<PaymentScheduleResponseDTO> paymentByLoanIdAndPaymentDate = paymentScheduleService.getPaymentByLoanIdAndPaymentDate(loanById.getId(), paymentRequest.getPaymentDate());
        System.out.println("--------------");
        System.out.println(paymentByLoanIdAndPaymentDate);

        BigDecimal monthlyPaymentAmount = null;
        LocalDate dateMonthlyPaymentAmount = null;
        boolean isPayment = false;
        for (PaymentScheduleResponseDTO scheduleResponseDTO : paymentByLoanIdAndPaymentDate) {
            monthlyPaymentAmount= scheduleResponseDTO.getMonthlyPaymentAmount();
            dateMonthlyPaymentAmount = scheduleResponseDTO.getPaymentDate();
            isPayment = scheduleResponseDTO.isPayment();
        }

        System.out.println("============================================================");
        System.out.println("monthlyPaymentAmount: "+monthlyPaymentAmount);
        System.out.println("dateMonthlyPaymentAmount: "+dateMonthlyPaymentAmount);
        System.out.println("============================================================");

        // Assuming dateMonthlyPaymentAmount is of type LocalDate
        LocalDate updatedDate = dateMonthlyPaymentAmount.plusDays(7);

        System.out.println("=========================");
        System.out.println("RequestPaymentDate: "+paymentRequest.getPaymentDate());
        System.out.println("=========================");

        // Update PaymentSchedule
        if (!paymentByLoanIdAndPaymentDate.isEmpty()) {
            if (paymentRequest.getPaymentAmount().compareTo(monthlyPaymentAmount) == 0 && paymentRequest.getPaymentDate().compareTo(updatedDate) <=0){
                if (isPayment == true){
                    throw new Exception("Payment has already been processed.");
                }
                Payment paymentResponse = paymentRepository.save(payment);
                // Update status for each PaymentSchedule
                for (PaymentScheduleResponseDTO scheduleResponseDTO : paymentByLoanIdAndPaymentDate) {
                    PaymentSchedule paymentSchedule = paymentScheduleRepository.findById(scheduleResponseDTO.getLoanId()).orElseThrow(() -> new Exception("Payment schedule not found"));

                    paymentSchedule.setStatus("OK");
                    paymentSchedule.setPayment(true);
                    paymentScheduleRepository.save(paymentSchedule);
                }

                // Customize response
                PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
                paymentResponseDTO.setLoanId(loanById.getId());
                paymentResponseDTO.setPaymentMethodId(paymentResponse.getId());
                paymentResponseDTO.setPaymentAmount(paymentResponse.getPaymentAmount());
                paymentResponseDTO.setPaymentDescription(paymentResponse.getPaymentDescription());
                paymentResponseDTO.setPaymentDate(paymentResponse.getPaymentDate());
                return paymentResponseDTO;
            }
            else if (paymentRequest.getPaymentAmount().compareTo(monthlyPaymentAmount) == 0 && paymentRequest.getPaymentDate().compareTo(updatedDate) > 0){
                Payment paymentResponse = paymentRepository.save(payment);
                if (isPayment == true){
                    throw new Exception("Payment has already been processed.");
                }

                // Update status for each PaymentSchedule
                for (PaymentScheduleResponseDTO scheduleResponseDTO : paymentByLoanIdAndPaymentDate) {
                    PaymentSchedule paymentSchedule = paymentScheduleRepository.findById(scheduleResponseDTO.getLoanId()).orElseThrow(() -> new Exception("Payment schedule not found"));

                    paymentSchedule.setStatus("LATE");
                    paymentSchedule.setPayment(true);
                    paymentScheduleRepository.save(paymentSchedule);
                }

                // Customize response
                PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
                paymentResponseDTO.setLoanId(loanById.getId());
                paymentResponseDTO.setPaymentMethodId(paymentResponse.getId());
                paymentResponseDTO.setPaymentAmount(paymentResponse.getPaymentAmount());
                paymentResponseDTO.setPaymentDescription(paymentResponse.getPaymentDescription());
                paymentResponseDTO.setPaymentDate(paymentResponse.getPaymentDate());
                paymentResponseDTO.setCreatedPaymentDate(paymentResponse.getCreatedPaymentDate());
                return paymentResponseDTO;
            }
            else{
                throw new Exception("You cannot do payment; it is the wrong payment amount. You need to pay: " + monthlyPaymentAmount);
            }
        } else {
            throw new Exception("You cannot do payment; it is the wrong payment schedule.");
        }
    }
}