package com.erp.greenlight.repositories;

import com.erp.greenlight.models.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierOrderRepo extends JpaRepository<SupplierOrder, Long> {

}
