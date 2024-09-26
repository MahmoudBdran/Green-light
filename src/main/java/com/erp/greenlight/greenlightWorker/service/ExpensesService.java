package com.erp.greenlight.greenlightWorker.service;
 
import com.erp.greenlight.greenlightWorker.models.Expenses;
import com.erp.greenlight.greenlightWorker.respository.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpensesService {

    @Autowired
    private com.erp.greenlight.greenlightWorker.respository.ExpensesRepository ExpensesRepository;

    public Expenses saveExpenses(Expenses Expenses) {
        return ExpensesRepository.save(Expenses);
    }

    public Optional<Expenses> findExpensesById(Long id) {
        return ExpensesRepository.findById(id);
    }

    public List<Expenses> findExpensesByProjectId(Long projectId, LocalDateTime fromDate, LocalDateTime toDate) {
        return ExpensesRepository.findByProjectIdAndUpdatedAtBetween(projectId, fromDate, toDate);
    }

    public void deleteExpensesById(Long id) {
        ExpensesRepository.deleteById(id);
    }

    public Expenses updateExpenses(Expenses Expenses) {
        return ExpensesRepository.save(Expenses);
    }
}

