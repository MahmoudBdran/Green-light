package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Supplier;
import com.erp.greenlight.models.SupplierCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierCategoryRepo extends JpaRepository<SupplierCategory,Long> {
    Supplier findByName(String name);
}
