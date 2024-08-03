package com.erp.greenlight.greenlightWorker.service;

import com.erp.greenlight.greenlightWorker.models.*;
import com.erp.greenlight.greenlightWorker.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinancialReportService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ExpensesRepository expensesRepository;

    @Autowired
    private OwnerPaymentRepository ownerPaymentRepository;

    public Map<String, Object> getOverallFinancialStatus() {
        Map<String, Object> financialStatus = new HashMap<>();

        List<Project> projects = projectRepository.findAll();
        BigDecimal totalSalaries = BigDecimal.ZERO;
        BigDecimal totalPayments = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;
        BigDecimal totalOwnerPayments = BigDecimal.ZERO;

        for (Project project : projects) {
            // Sum salaries for the project
            List<Salary> salaries = salaryRepository.findByProjectId(project.getId());
            totalSalaries = totalSalaries.add(
                    salaries.stream()
                            .map(Salary::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
            );

            // Sum payments for the project
            List<Payment> payments = paymentRepository.findByProjectId(project.getId());
            totalPayments = totalPayments.add(
                    payments.stream()
                            .map(Payment::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
            );

            // Sum expenses for the project
            List<Expenses> expenses = expensesRepository.findByProjectId(project.getId());
            totalExpenses = totalExpenses.add(
                    expenses.stream()
                            .map(Expenses::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
            );

            // Sum owner payments for the project
            List<OwnerPayment> ownerPayments = ownerPaymentRepository.findByProject(project);
            totalOwnerPayments = totalOwnerPayments.add(
                    ownerPayments.stream()
                            .map(OwnerPayment::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
            );
        }

        BigDecimal totalDue = totalSalaries.subtract(totalPayments);
        BigDecimal totalEarnings = totalOwnerPayments.subtract(totalDue.add(totalExpenses));

        financialStatus.put("totalSalaries", totalSalaries);
        financialStatus.put("totalPayments", totalPayments);
        financialStatus.put("totalExpenses", totalExpenses);
        financialStatus.put("totalOwnerPayments", totalOwnerPayments);
        financialStatus.put("totalDue", totalDue);
        financialStatus.put("totalEarnings", totalEarnings);

        return financialStatus;
    }
}

