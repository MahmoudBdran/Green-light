package com.erp.greenlight.greenlightWorker.controllers;

import com.erp.greenlight.greenlightWorker.models.Payment;
import com.erp.greenlight.greenlightWorker.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.savePayment(payment);
        return ResponseEntity.ok(savedPayment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Payment>> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.findPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<Payment>> getPaymentsByWorkerId(@PathVariable Long workerId) {
        List<Payment> payments = paymentService.findPaymentsByWorkerId(workerId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Payment>> getPaymentsByProjectId(@PathVariable Long projectId) {
        List<Payment> payments = paymentService.findPaymentsByProjectId(projectId);
        return ResponseEntity.ok(payments);
    }
}

