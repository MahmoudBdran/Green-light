package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SupplierOrderRepo extends JpaRepository<SupplierOrder, Long> {


    @Query("SELECT SUM(s.moneyForAccount) FROM SupplierOrder s WHERE s.account=:account")
    BigDecimal getNet(Account account);
}
