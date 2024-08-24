package com.erp.greenlight.greenlightWorker.controllers;

import com.erp.greenlight.greenlightWorker.models.OwnerPayment;
import com.erp.greenlight.greenlightWorker.service.OwnerPaymentService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner-payments")
@CrossOrigin(origins = {"http://localhost", "https://animated-sprinkles-d16b69.netlify.app"})
public class OwnerPaymentController {

    @Autowired
    private OwnerPaymentService ownerPaymentService;

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Object>getPaymentsByProject(@PathVariable Long projectId) {

        return AppResponse.generateResponse("all_data", HttpStatus.OK,  ownerPaymentService.getPaymentsByProject(projectId) , true);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Object> getPaymentById(@PathVariable Long paymentId) {
        return AppResponse.generateResponse("all_data", HttpStatus.OK, ownerPaymentService.getPaymentById(paymentId) , true);
    }

    @PostMapping
    public ResponseEntity<Object> createPayment(@RequestBody OwnerPayment payment) {
        return AppResponse.generateResponse("تم حفظ الدفعة بنجاح", HttpStatus.OK,ownerPaymentService.createPayment(payment) , true);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<Object> updatePayment(@PathVariable Long paymentId, @RequestBody OwnerPayment updatedPayment) {
        return AppResponse.generateResponse("تم حفظ الدفعة بنجاح", HttpStatus.OK, ownerPaymentService.updatePayment(paymentId, updatedPayment) , true);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Object> deletePayment(@PathVariable Long paymentId) {
        ownerPaymentService.deletePayment(paymentId);
        return AppResponse.generateResponse("تم حذف الدفعة بنجاح", HttpStatus.OK, null, true);
    }
}

