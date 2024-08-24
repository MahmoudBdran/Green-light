package com.erp.greenlight.greenlightWorker.controllers;

import com.erp.greenlight.greenlightWorker.models.OwnerPayment;
import com.erp.greenlight.greenlightWorker.service.OwnerPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner-payments")
@CrossOrigin(origins = {"http://localhost", "https://animated-sprinkles-d16b69.netlify.app"})
public class OwnerPaymentController {

    @Autowired
    private OwnerPaymentService ownerPaymentService;

    @GetMapping("/project/{projectId}")
    public List<OwnerPayment> getPaymentsByProject(@PathVariable Long projectId) {
        return ownerPaymentService.getPaymentsByProject(projectId);
    }

    @GetMapping("/{paymentId}")
    public OwnerPayment getPaymentById(@PathVariable Long paymentId) {
        return ownerPaymentService.getPaymentById(paymentId);
    }

    @PostMapping
    public OwnerPayment createPayment(@RequestBody OwnerPayment payment) {
        return ownerPaymentService.createPayment(payment);
    }

    @PutMapping("/{paymentId}")
    public OwnerPayment updatePayment(@PathVariable Long paymentId, @RequestBody OwnerPayment updatedPayment) {
        return ownerPaymentService.updatePayment(paymentId, updatedPayment);
    }

    @DeleteMapping("/{paymentId}")
    public void deletePayment(@PathVariable Long paymentId) {
        ownerPaymentService.deletePayment(paymentId);
    }
}

