package com.erp.greenlight.greenlightWorker.respository;

import com.erp.greenlight.greenlightWorker.models.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

    List<Expenses> findByProjectId(Long projectId);
}
