package com.erp.greenlight.greenlightWorker.service;

import com.erp.greenlight.greenlightWorker.models.Payment;
import com.erp.greenlight.greenlightWorker.respository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Optional<Payment> findPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> findPaymentsByWorkerId(Long workerId) {
        return paymentRepository.findByWorkerId(workerId);
    }

    public List<Payment> findPaymentsByProjectId(Long projectId) {
        return paymentRepository.findByProjectId(projectId);
    }
}

