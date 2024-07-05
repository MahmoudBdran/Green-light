package com.erp.greenlight.repositories;

import com.erp.greenlight.models.InvStoresInventory;
import com.erp.greenlight.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<InvStoresInventory,Long> {
}
