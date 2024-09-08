package com.erp.greenlight.greenlightWorker.controllers;

import com.erp.greenlight.greenlightWorker.models.Payment;
import com.erp.greenlight.greenlightWorker.service.PaymentService;
import com.erp.greenlight.greenlightWorker.service.ProjectService;
import com.erp.greenlight.greenlightWorker.service.WorkerService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = {"http://localhost", "https://animated-sprinkles-d16b69.netlify.app"})
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/getAllData")
    public ResponseEntity<Object> getAllData() {
        Map<String, Object> data = new HashMap<>();

        data.put("workers", workerService.findAllWorkers());
        data.put("projects", projectService.findAllProjects());

        return AppResponse.generateResponse("all_data", HttpStatus.OK,  data , true);
    }

    @PostMapping
    public ResponseEntity<Object> createPayment(@RequestBody Payment payment) {
        return AppResponse.generateResponse("تم حفظ اليومية بنجاح", HttpStatus.OK, paymentService.savePayment(payment) , true);
    }

    @PutMapping
    public ResponseEntity<Object> updatePayment(@RequestBody Payment payment) {
        return AppResponse.generateResponse("تم حفظ اليومية بنجاح", HttpStatus.OK, paymentService.savePayment(payment) , true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Payment>> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.findPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/worker/{workerId}")
    public ResponseEntity<Object> getPaymentsByWorkerId(@PathVariable Long workerId) {

        return AppResponse.generateResponse("all_data", HttpStatus.OK,  paymentService.findPaymentsByWorkerId(workerId) , true);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Object> getPaymentsByProjectId(@PathVariable Long projectId) {
        return AppResponse.generateResponse("all_data", HttpStatus.OK,   paymentService.findPaymentsByProjectId(projectId) , true);
    }
}

