package com.erp.greenlight.greenlightWorker.controllers;

import com.erp.greenlight.greenlightWorker.models.Expenses;
import com.erp.greenlight.greenlightWorker.service.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Expenses> createExpenses(@RequestBody Expenses Expenses) {
        Expenses savedExpenses = ExpensesService.saveExpenses(Expenses);
        return ResponseEntity.ok(savedExpenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Expenses>> getExpensesById(@PathVariable Long id) {
        Optional<Expenses> Expenses = ExpensesService.findExpensesById(id);
        return ResponseEntity.ok(Expenses);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Expenses>> getExpensessByProjectId(@PathVariable Long projectId) {
        List<Expenses> Expensess = ExpensesService.findExpensessByProjectId(projectId);
        return ResponseEntity.ok(Expensess);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpenses(@PathVariable Long id) {
        ExpensesService.deleteExpensesById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expenses> updateExpenses(@PathVariable Long id, @RequestBody Expenses Expenses) {
        Expenses.setId(id);
        Expenses updatedExpenses = ExpensesService.updateExpenses(Expenses);
        return ResponseEntity.ok(updatedExpenses);
    }
}

