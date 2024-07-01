package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Customer;
import com.erp.greenlight.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier,Long> {
    Supplier findByName(String name);

    Supplier findByAccountId(Long accountId);

    @Query("SELECT COUNT(s) FROM Supplier s")
    long countAllRows();
}
