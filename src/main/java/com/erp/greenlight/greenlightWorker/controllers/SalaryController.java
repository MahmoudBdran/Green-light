package com.erp.greenlight.greenlightWorker.controllers;

import com.erp.greenlight.greenlightWorker.models.Salary;
import com.erp.greenlight.greenlightWorker.service.SalaryService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> createSalary(@RequestBody Salary salary) {
        return AppResponse.generateResponse("تم حفظ المرتب بنجاح", HttpStatus.OK,   salaryService.saveSalary(salary) , true);
    }

    @PutMapping
    public ResponseEntity<Object> updateSalary(@RequestBody Salary salary) {
        return AppResponse.generateResponse("تم حفظ المرتب بنجاح", HttpStatus.OK,   salaryService.saveSalary(salary) , true);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getSalaryById(@PathVariable Long id) {
        return AppResponse.generateResponse("all_data", HttpStatus.OK,  salaryService.findSalaryById(id), true);
    }

    @GetMapping("/worker/{workerId}")
    public ResponseEntity<Object> getSalariesByWorkerId(@PathVariable Long workerId) {
        return AppResponse.generateResponse("all_data", HttpStatus.OK, salaryService.findSalariesByWorkerId(workerId), true);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Object> getSalariesByProjectId(@PathVariable Long projectId) {
        return AppResponse.generateResponse("all_data", HttpStatus.OK, salaryService.findSalariesByProjectId(projectId), true);
    }
}

