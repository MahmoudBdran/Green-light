package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.Customer;
import com.erp.greenlight.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Customer findByName(String name);

    Customer findByAccountId(Long accountId);

    @Query("SELECT COUNT(c) FROM Customer c")
    long countAllRows();

}
