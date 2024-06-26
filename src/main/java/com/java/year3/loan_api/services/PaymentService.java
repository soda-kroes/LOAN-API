package com.java.year3.loan_api.services;

import com.java.year3.loan_api.dto.request.PaymentRequestDTO;
import com.java.year3.loan_api.dto.response.PaymentResponseDTO;
import com.java.year3.loan_api.entity.Payment;
import com.java.year3.loan_api.exception.NotFoundException;

public interface PaymentService {
    PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO) throws Exception;
}
