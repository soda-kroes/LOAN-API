package com.java.year3.loan_api.controller;

import com.java.year3.loan_api.dto.response.PaymentScheduleResponseDTO;
import com.java.year3.loan_api.dto.response.StatusResponseDTO;
import com.java.year3.loan_api.entity.PaymentSchedule;
import com.java.year3.loan_api.services.PaymentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment_schedule")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentScheduleController {
    private final PaymentScheduleService paymentScheduleService;

    @GetMapping("/get-byLoanId/{id}/{paymentDate}")
    public ResponseEntity<Map<String, Object>> getAllPaymentSchedules(@PathVariable("id") Long loanId, @PathVariable("paymentDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate paymentDate) throws Exception {
        Map<String, Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();

        List<PaymentScheduleResponseDTO> paymentSchedules = paymentScheduleService.getPaymentByLoanIdAndPaymentDate(loanId, paymentDate);

        if (!paymentSchedules.isEmpty()) {
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Payment schedules retrieved successfully");
            response.put("data", paymentSchedules);
        } else {
            statusResponseDTO.setErrorCode(204);
            statusResponseDTO.setErrorMessage("No payment schedules found for the given loan ID and payment date");
            response.put("data", null);
        }

        response.put("status", statusResponseDTO);
        return ResponseEntity.ok(response);
    }
}