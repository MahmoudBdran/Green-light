package com.erp.greenlight.greenlightWorker.controllers;


import com.erp.greenlight.greenlightWorker.dto.WorkerTransactionHistoryDTO;
import com.erp.greenlight.greenlightWorker.models.Worker;
import com.erp.greenlight.greenlightWorker.service.WorkerService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/workers")
@CrossOrigin(origins = {"http://localhost", "https://animated-sprinkles-d16b69.netlify.app"})
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @PostMapping("/create")
    public ResponseEntity<Object> createWorker(@RequestBody Worker worker) {
        Worker savedWorker = workerService.saveWorker(worker);
        return AppResponse.generateResponse("تم حفظ العامل بنجاح", HttpStatus.OK,savedWorker , true);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllWorkers() {

        return AppResponse.generateResponse("all_data", HttpStatus.OK,  workerService.findAllWorkers() , true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getWorkerById(@PathVariable Long id) {
        return AppResponse.generateResponse("all_data", HttpStatus.OK,  workerService.findWorkerById(id) , true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWorker(@PathVariable Long id) {
        workerService.deleteWorkerById(id);
        return AppResponse.generateResponse("تم حذف العامل بنجاح", HttpStatus.OK,   null, true);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateWorker(@PathVariable Long id, @RequestBody Worker worker) {
        worker.setId(id);
        Worker updatedWorker = workerService.updateWorker(worker);
        return AppResponse.generateResponse("تم تحديث العامل بنجاح", HttpStatus.OK,   updatedWorker, true);
    }
    @GetMapping("/{id}/history")
    public WorkerTransactionHistoryDTO getWorkerTransactionHistory(@PathVariable Long id) {
        return workerService.getWorkerTransactionHistory(id);
//        Map<String, Object> history = workerService.getWorkerTransactionHistory(id);
//        return AppResponse.generateResponse("Worker transaction history fetched successfully", HttpStatus.OK, history, true);
    }

    @GetMapping("/financial-status")
    public ResponseEntity<Object> getWorkersFinancialStatus() {
        List<Map<String, Object>> financialStatus = workerService.getAllWorkersFinancialStatus();
        return ResponseEntity.ok(financialStatus);
    }

}

