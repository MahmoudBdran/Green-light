package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.SalesInvoice;
import com.erp.greenlight.models.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SalesInvoiceRepo extends JpaRepository<SalesInvoice, Long> {

    @Query("SELECT SUM(s.moneyForAccount) FROM SalesInvoice s WHERE s.account=:account")
    BigDecimal netSalesInvoicesForCustomer(Account account);
}
