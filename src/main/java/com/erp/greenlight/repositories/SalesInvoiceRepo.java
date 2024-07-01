package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.SalesInvoice;
import com.erp.greenlight.models.SupplierOrder;
import com.erp.greenlight.models.TreasuryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SalesInvoiceRepo extends JpaRepository<SalesInvoice, Long> {

    @Query("SELECT SUM(s.moneyForAccount) FROM SalesInvoice s WHERE s.account=:account")
    BigDecimal netSalesInvoicesForCustomer(Account account);


    @Query(value = "SELECT * FROM sales_invoices ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<SalesInvoice> getLast5Sales();



    @Query("SELECT MONTH(invoiceDate) as month, COUNT(s) as count FROM SalesInvoice s GROUP BY MONTH(invoiceDate) ORDER BY MONTH(invoiceDate)")
    List<Object[]> findSalesInvoiceCountsByMonth();




}
