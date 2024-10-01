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
import java.time.LocalDateTime;
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



    public List<Expenses> getProjectExpenses(Long projectId, LocalDateTime fromDate ,LocalDateTime toDate) {
        return expensesRepository.findByProjectIdAndUpdatedAtBetween(projectId, fromDate, toDate);
    }
    public ProjectFinancialReportDTO getProjectFinancialReport(Long projectId, LocalDateTime fromDate ,LocalDateTime toDate) {
        Project project = projectRepository.findById(projectId).get();


        // Get all salaries for the project
        List<Salary> salaries = salaryRepository.findByProjectIdAndUpdatedAtBetween(projectId,fromDate,toDate);
        BigDecimal totalSalaries = salaries.stream()
                .map(Salary::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Get all payments for the project
        List<Payment> payments = paymentRepository.findByProjectIdAndUpdatedAtBetween(projectId,fromDate,toDate);
        BigDecimal totalPayments = payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate total due
        BigDecimal totalDue = totalSalaries.subtract(totalPayments);

        // Get all expenses for the project
        List<Expenses> expenses = expensesRepository.findByProjectIdAndUpdatedAtBetween(projectId, fromDate, toDate);
        BigDecimal totalExpenses = expenses.stream()
                .map(Expenses::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Get all owner payments for the project
        List<OwnerPayment> ownerPayments = ownerPaymentRepository.findByProjectAndUpdatedAtBetween(project, fromDate, toDate);
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

