package com.java.year3.loan_api.controller;

import com.java.year3.loan_api.dto.request.PaymentRequestDTO;
import com.java.year3.loan_api.dto.response.PaymentResponseDTO;
import com.java.year3.loan_api.dto.response.StatusResponseDTO;
import com.java.year3.loan_api.entity.Payment;
import com.java.year3.loan_api.entity.UserRole;
import com.java.year3.loan_api.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO) throws Exception {
        Map<String,Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        PaymentResponseDTO data = paymentService.createPayment(paymentRequestDTO);
        if(data != null) {
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Payment  created successfully");
            response.put("data", data);
        }else{
            statusResponseDTO.setErrorCode(204); // No Content
            statusResponseDTO.setErrorMessage("Your Content Unexceptionable!");
            response.put("data", null);
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
