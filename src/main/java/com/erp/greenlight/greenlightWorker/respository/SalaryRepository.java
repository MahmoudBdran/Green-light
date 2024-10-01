package com.erp.greenlight.greenlightWorker.respository;

import com.erp.greenlight.greenlightWorker.models.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
   List<Salary> findByWorkerIdOrderBySalaryDateDesc(Long workerId);
   List<Salary> findByWorkerIdAndUpdatedAtBetween(Long workerId, LocalDateTime from, LocalDateTime to);
    List<Salary> findByProjectId(Long projectId);
    List<Salary> findByProjectIdAndUpdatedAtBetween(Long projectId, LocalDateTime from, LocalDateTime to);
    @Query("SELECT SUM(s.amount) FROM Salary s WHERE s.worker.id = :workerId and s.updatedAt between :from and :to")
    BigDecimal sumByWorkerId( Long workerId,LocalDateTime from,LocalDateTime to);
}
