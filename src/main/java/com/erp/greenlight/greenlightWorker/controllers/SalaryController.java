package com.erp.greenlight.greenlightWorker.controllers;

import com.erp.greenlight.greenlightWorker.models.Salary;
import com.erp.greenlight.greenlightWorker.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping
    public ResponseEntity<Salary> createSalary(@RequestBody Salary salary) {
        Salary savedSalary = salaryService.saveSalary(salary);
        return ResponseEntity.ok(savedSalary);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Salary>> getSalaryById(@PathVariable Long id) {
        Optional<Salary> salary = salaryService.findSalaryById(id);
        return ResponseEntity.ok(salary);
    }

    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<Salary>> getSalariesByWorkerId(@PathVariable Long workerId) {
        List<Salary> salaries = salaryService.findSalariesByWorkerId(workerId);
        return ResponseEntity.ok(salaries);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Salary>> getSalariesByProjectId(@PathVariable Long projectId) {
        List<Salary> salaries = salaryService.findSalariesByProjectId(projectId);
        return ResponseEntity.ok(salaries);
    }
}

