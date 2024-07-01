package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Customer;
import com.erp.greenlight.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Customer findByName(String name);

    Customer findByAccountId(Long accountId);

    @Query("SELECT COUNT(c) FROM Customer c")
    long countAllRows();

}
