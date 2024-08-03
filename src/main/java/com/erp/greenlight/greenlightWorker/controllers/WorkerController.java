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
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @PostMapping("/create")
    public ResponseEntity<Worker> createWorker(@RequestBody Worker worker) {
        Worker savedWorker = workerService.saveWorker(worker);
        return ResponseEntity.ok(savedWorker);
    }

    @GetMapping()
    public ResponseEntity<List<Worker>> getAllWorkers() {
        List<Worker> workers = workerService.findAllWorkers();
        return ResponseEntity.ok(workers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Worker>> getWorkerById(@PathVariable Long id) {
        Optional<Worker> worker = workerService.findWorkerById(id);
        return ResponseEntity.ok(worker);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long id) {
        workerService.deleteWorkerById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Worker> updateWorker(@PathVariable Long id, @RequestBody Worker worker) {
        worker.setId(id);
        Worker updatedWorker = workerService.updateWorker(worker);
        return ResponseEntity.ok(updatedWorker);
    }
    @GetMapping("/{id}/history")
    public WorkerTransactionHistoryDTO getWorkerTransactionHistory(@PathVariable Long id) {
        return workerService.getWorkerTransactionHistory(id);
 }
    @GetMapping("/financial-status")
    public ResponseEntity<List<Map<String, Object>>> getWorkersFinancialStatus() {
        List<Map<String, Object>> financialStatus = workerService.getWorkersFinancialStatus();
        return ResponseEntity.ok(financialStatus);
    }
//    @GetMapping("/search")
//    public ResponseEntity<List<Worker>> searchWorkers(@RequestParam String name, @RequestParam Long projectId) {
//        List<Worker> workers = workerService.findWorkersByNameAndProject(name, projectId);
//        return ResponseEntity.ok(workers);
//    }
}

