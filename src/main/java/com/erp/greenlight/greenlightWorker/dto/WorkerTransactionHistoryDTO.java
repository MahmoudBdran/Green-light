package com.erp.greenlight.greenlightWorker.dto;

import com.erp.greenlight.greenlightWorker.models.Payment;
import com.erp.greenlight.greenlightWorker.models.Salary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkerTransactionHistoryDTO {
    private String workerName;
    private List<Salary> salaries;
    private List<Payment> payments;
    private BigDecimal totalSalary;
    private BigDecimal totalPaid;
    private BigDecimal remainingSalary;

    // Getters and setters
}
