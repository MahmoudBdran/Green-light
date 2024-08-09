package com.erp.greenlight.greenlightWorker.controllers;

import com.erp.greenlight.greenlightWorker.models.Expenses;
import com.erp.greenlight.greenlightWorker.service.ExpensesService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    @Autowired
    private ExpensesService ExpensesService;

    @PostMapping("")
    public ResponseEntity<Object> createExpenses(@RequestBody Expenses Expenses) {
        return AppResponse.generateResponse("تم حفظ المصروفات بنجاح", HttpStatus.OK, ExpensesService.saveExpenses(Expenses) , true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Expenses>> getExpensesById(@PathVariable Long id) {
        Optional<Expenses> Expenses = ExpensesService.findExpensesById(id);
        return ResponseEntity.ok(Expenses);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Object> getExpensesByProjectId(@PathVariable Long projectId) {
        return AppResponse.generateResponse("all_data", HttpStatus.OK, ExpensesService.findExpensessByProjectId(projectId), true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpenses(@PathVariable Long id) {
        ExpensesService.deleteExpensesById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateExpenses(@PathVariable Long id, @RequestBody Expenses Expenses) {
        Expenses.setId(id);
        Expenses  updatedExpenses = ExpensesService.updateExpenses(Expenses);
        return AppResponse.generateResponse("تم حفظ المصروفات بنجاح", HttpStatus.OK, updatedExpenses, true);
    }
}

