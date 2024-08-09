package com.erp.greenlight.greenlightWorker.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFinancialReportDTO {
    private String projectName;
    private BigDecimal totalSalaries;
    private BigDecimal totalPayments;
    private BigDecimal totalDue;
    private BigDecimal totalExpenses;
    private BigDecimal totalOwnerPayments;
    private BigDecimal totalProjectEarnings;

    // Getters and setters
}

