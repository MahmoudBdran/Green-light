package com.erp.greenlight.greenlightWorker.service;

import com.erp.greenlight.greenlightWorker.models.OwnerPayment;
import com.erp.greenlight.greenlightWorker.models.Project;
import com.erp.greenlight.greenlightWorker.respository.OwnerPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerPaymentService {

    @Autowired
    private OwnerPaymentRepository ownerPaymentRepository;

    public List<OwnerPayment> getPaymentsByProject(Long projectId) {
        return ownerPaymentRepository.findByProject(new Project(projectId));
    }

    public OwnerPayment getPaymentById(Long paymentId) {
        return ownerPaymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public OwnerPayment createPayment(OwnerPayment payment) {
        return ownerPaymentRepository.save(payment);
    }

    public OwnerPayment updatePayment(Long paymentId, OwnerPayment updatedPayment) {
        OwnerPayment existingPayment = getPaymentById(paymentId);
        existingPayment.setAmount(updatedPayment.getAmount());
        existingPayment.setDescription(updatedPayment.getDescription());
        existingPayment.setPaymentDate(updatedPayment.getPaymentDate());
        return ownerPaymentRepository.save(existingPayment);
    }

    public void deletePayment(Long paymentId) {
        ownerPaymentRepository.deleteById(paymentId);
    }
}

