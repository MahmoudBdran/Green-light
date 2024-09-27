package com.erp.greenlight.greenlightWorker.service;

import com.erp.greenlight.greenlightWorker.models.Salary;
import com.erp.greenlight.greenlightWorker.respository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    public Salary saveSalary(Salary salary) {
        if(!salary.getIsPaid()){
            salary.setDeduction(BigDecimal.ZERO);
        }
        return salaryRepository.save(salary);
    }

    public void saveListOfSalaries(List <Salary> salaries) {

        salaries.forEach( salary -> {
            if(!salary.getIsPaid()){
                salary.setDeduction(BigDecimal.ZERO);
            }
            salaryRepository.save(salary);
        });
    }

    public Optional<Salary> findSalaryById(Long id) {
        return salaryRepository.findById(id);
    }

    public List<Salary> findSalariesByWorkerId(Long workerId) {
        return salaryRepository.findByWorkerId(workerId);
    }

    public List<Salary> findSalariesByProjectId(Long projectId) {
        return salaryRepository.findByProjectId(projectId);
    }
}

