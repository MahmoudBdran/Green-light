package com.erp.greenlight.greenlightWorker.respository;

import com.erp.greenlight.greenlightWorker.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByWorkerId(Long workerId);
    List<Payment> findByProjectId(Long projectId);
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.worker.id = :workerId")
    BigDecimal sumByWorkerId(@Param("workerId") Long workerId);
}
