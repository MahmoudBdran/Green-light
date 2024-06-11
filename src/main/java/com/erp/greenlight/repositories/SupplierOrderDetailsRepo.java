package com.erp.greenlight.repositories;

import com.erp.greenlight.models.SupplierOrder;
import com.erp.greenlight.models.SupplierOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierOrderDetailsRepo extends JpaRepository<SupplierOrderDetails, Long> {

    Optional <SupplierOrderDetails> findByOrderId(Long id);
}
