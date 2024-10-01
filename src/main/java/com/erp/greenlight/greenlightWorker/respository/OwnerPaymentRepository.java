package com.erp.greenlight.greenlightWorker.respository;

import com.erp.greenlight.greenlightWorker.models.OwnerPayment;
import com.erp.greenlight.greenlightWorker.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OwnerPaymentRepository extends JpaRepository<OwnerPayment, Long> {
    List<OwnerPayment> findByProjectAndUpdatedAtBetween(Project project, LocalDateTime from, LocalDateTime to);
}
