package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.SupplierOrder;
import com.erp.greenlight.models.SupplierOrderReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SupplierOrderReturnRepo extends JpaRepository<SupplierOrderReturn, Long> {


    @Query("SELECT SUM(s.moneyForAccount) FROM SupplierOrderReturn s WHERE s.account=:account")
    BigDecimal getNet(Account account);
}
