package com.erp.greenlight.repositories;

import com.erp.greenlight.enums.SupplierOrderType;
import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.SupplierOrder;
import com.erp.greenlight.models.SupplierOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierOrderRepo extends JpaRepository<SupplierOrder, Long> {

    @Query("SELECT SUM(s.moneyForAccount) FROM SupplierOrder s WHERE s.account=:account")
    BigDecimal getNet(Account account);

    List<SupplierOrder> findAllByOrderType(SupplierOrderType orderType);
}
