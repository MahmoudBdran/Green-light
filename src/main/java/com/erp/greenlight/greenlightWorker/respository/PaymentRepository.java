package com.erp.greenlight.greenlightWorker.respository;

import com.erp.greenlight.greenlightWorker.models.Payment;
import com.erp.greenlight.greenlightWorker.models.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByWorkerId(Long workerId);

    List<Payment> findByWorkerIdAndUpdatedAtBetween(Long workerId, LocalDateTime from, LocalDateTime to);
    List<Payment> findByProjectId(Long projectId);
    List<Payment> findByProjectIdAndUpdatedAtBetween(Long projectId, LocalDateTime from, LocalDateTime to);
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.worker.id = :workerId and p.updatedAt between :from and :to")
    BigDecimal sumByWorkerId(@Param("workerId") Long workerId,LocalDateTime from,LocalDateTime to);
}
