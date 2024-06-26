package com.java.year3.loan_api.services.impl;

import com.java.year3.loan_api.entity.PaymentMethod;
import com.java.year3.loan_api.exception.NotFoundException;
import com.java.year3.loan_api.repository.PaymentMethodRepository;
import com.java.year3.loan_api.services.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethod getPaymentMethodById(Long id) throws NotFoundException {
        return paymentMethodRepository.findById(id).orElseThrow(()-> new NotFoundException("Payment method with id " + id + " not found."));
    }
}
