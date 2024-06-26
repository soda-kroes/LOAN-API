package com.java.year3.loan_api.services;

import com.java.year3.loan_api.entity.PaymentMethod;
import com.java.year3.loan_api.exception.NotFoundException;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethod> getPaymentMethods();
    PaymentMethod getPaymentMethodById(Long id) throws NotFoundException;
}
