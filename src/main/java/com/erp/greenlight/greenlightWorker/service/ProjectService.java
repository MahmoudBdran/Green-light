package com.erp.greenlight.greenlightWorker.service;


import com.erp.greenlight.greenlightWorker.dto.ProjectFinancialReportDTO;
import com.erp.greenlight.greenlightWorker.models.*;
import com.erp.greenlight.greenlightWorker.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private SalaryRepository salaryRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ExpensesRepository expensesRepository;
    @Autowired
    private PaymentRepository paymentRepository;


    @Autowired
    private OwnerPaymentRepository ownerPaymentRepository;

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Page<Project> findAllProjects(int pageIndex, int pageSize) {
        Pageable page = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return projectRepository.findAll(page);
    }
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }
    public Optional<Project> findProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }

    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }



    public List<Expenses> getProjectExpenses(Long projectId) {
        return expensesRepository.findByProjectId(projectId);
    }
    public ProjectFinancialReportDTO getProjectFinancialReport(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Get all salaries for the project
        List<Salary> salaries = salaryRepository.findByProjectId(projectId);
        BigDecimal totalSalaries = salaries.stream()
                .map(Salary::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Get all payments for the project
        List<Payment> payments = paymentRepository.findByProjectId(projectId);
        BigDecimal totalPayments = payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate total due
        BigDecimal totalDue = totalSalaries.subtract(totalPayments);

        // Get all expenses for the project
        List<Expenses> expenses = expensesRepository.findByProjectId(projectId);
        BigDecimal totalExpenses = expenses.stream()
                .map(Expenses::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Get all owner payments for the project
        List<OwnerPayment> ownerPayments = ownerPaymentRepository.findByProject(project);
        BigDecimal totalOwnerPayments = ownerPayments.stream()
                .map(OwnerPayment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate final result
        BigDecimal totalProjectEarnings = totalOwnerPayments.subtract(totalSalaries.add(totalExpenses));

        ProjectFinancialReportDTO report = new ProjectFinancialReportDTO();
        report.setProjectName(project.getProjectName());
        report.setTotalSalaries(totalSalaries);
        report.setTotalPayments(totalPayments);
        report.setTotalDue(totalDue);
        report.setTotalExpenses(totalExpenses);
        report.setTotalOwnerPayments(totalOwnerPayments);
        report.setTotalProjectEarnings(totalProjectEarnings);

        return report;
    }
}

