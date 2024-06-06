package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Customer;
import com.erp.greenlight.models.InvItemcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Customer findByName(String name);
}
