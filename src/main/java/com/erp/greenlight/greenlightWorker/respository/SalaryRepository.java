package com.erp.greenlight.greenlightWorker.respository;

import com.erp.greenlight.greenlightWorker.models.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findByWorkerId(Long workerId);
    List<Salary> findByProjectId(Long projectId);
    @Query("SELECT SUM(s.amount) FROM Salary s WHERE s.worker.id = :workerId")
    BigDecimal sumByWorkerId( Long workerId);
}
