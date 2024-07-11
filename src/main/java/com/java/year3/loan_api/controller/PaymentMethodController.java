package com.java.year3.loan_api.controller;

import com.java.year3.loan_api.dto.response.StatusResponseDTO;
import com.java.year3.loan_api.entity.PaymentMethod;
import com.java.year3.loan_api.services.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment-method")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPaymentMethods() {
        Map<String,Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        List<PaymentMethod> data = paymentMethodService.getPaymentMethods();
        if (data.isEmpty()){
            statusResponseDTO.setErrorCode(204);
            statusResponseDTO.setErrorMessage("Payment Method List is empty");
            response.put("data", null);
        }else{
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Payment Method list retrieved successfully");
            response.put("data", data);
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) throws Exception {
        Map<String,Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        PaymentMethod data = paymentMethodService.getPaymentMethodById(id);
        if(data != null) {
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("Payment method getById successfully");
            response.put("data", data);
        }else{
            statusResponseDTO.setErrorCode(204);
            statusResponseDTO.setErrorMessage("Your Content Unexceptionable!");
            response.put("data", null);
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
