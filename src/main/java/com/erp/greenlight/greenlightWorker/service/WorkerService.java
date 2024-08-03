package com.erp.greenlight.greenlightWorker.service;


import com.erp.greenlight.greenlightWorker.dto.WorkerTransactionHistoryDTO;
import com.erp.greenlight.greenlightWorker.models.Payment;
import com.erp.greenlight.greenlightWorker.models.Salary;
import com.erp.greenlight.greenlightWorker.models.Worker;
import com.erp.greenlight.greenlightWorker.respository.PaymentRepository;
import com.erp.greenlight.greenlightWorker.respository.SalaryRepository;
import com.erp.greenlight.greenlightWorker.respository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public Worker saveWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public List<Worker> findAllWorkers() {
        return workerRepository.findAll();
    }

    public Optional<Worker> findWorkerById(Long id) {
        return workerRepository.findById(id);
    }

    public void deleteWorkerById(Long id) {
        workerRepository.deleteById(id);
    }

    public Worker updateWorker(Worker worker) {
        return workerRepository.save(worker);
    }
    public WorkerTransactionHistoryDTO getWorkerTransactionHistory(Long workerId) {
//        List<Salary> salaries = salaryRepository.findByWorkerId(workerId);
//        List<Payment> payments = paymentRepository.findByWorkerId(workerId);
//
//        Map<String, Object> history = new HashMap<>();
//        history.put("salaries", salaries);
//        history.put("payments", payments);
//
//        return history;
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        List<Salary> salaries = salaryRepository.findByWorkerId(workerId);
        List<Payment> payments = paymentRepository.findByWorkerId(workerId);

        BigDecimal totalSalary = salaries.stream()
                .map(Salary::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPaid = payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal remainingSalary = totalSalary.subtract(totalPaid);

        WorkerTransactionHistoryDTO history = new WorkerTransactionHistoryDTO();
        history.setWorkerName(worker.getName());
        history.setSalaries(salaries);
        history.setPayments(payments);
        history.setTotalSalary(totalSalary);
        history.setTotalPaid(totalPaid);
        history.setRemainingSalary(remainingSalary);

        return history;
    }


    public List<Map<String, Object>> getAllWorkersFinancialStatus() {
        List<Map<String, Object>> financialStatus = new ArrayList<>();

        // Retrieve all workers
        List<Worker> workers = workerRepository.findAll();

        for (Worker worker : workers) {
            Map<String, Object> workerStatus = new HashMap<>();
            workerStatus.put("workerId", worker.getId());
            workerStatus.put("name", worker.getName());

            // Calculate total earned, total paid, and total remaining
            BigDecimal totalSalaries = salaryRepository.sumByWorkerId(worker.getId());
            if (totalSalaries == null) {
                totalSalaries = BigDecimal.ZERO;
            }

            BigDecimal totalPaid = paymentRepository.sumByWorkerId(worker.getId());
            if (totalPaid == null) {
                totalPaid = BigDecimal.ZERO;
            }

            BigDecimal totalRemaining = totalSalaries.subtract(totalPaid);

            workerStatus.put("totalSalaries", totalSalaries);
            workerStatus.put("totalPaid", totalPaid);
            workerStatus.put("totalRemaining", totalRemaining);

            financialStatus.add(workerStatus);
        }

        return financialStatus;
    }
//    public List<Worker> findWorkersByNameAndProject(String name, Long projectId) {
//        return workerRepository.findByNameAndProject(name, projectId);
//    }
}

