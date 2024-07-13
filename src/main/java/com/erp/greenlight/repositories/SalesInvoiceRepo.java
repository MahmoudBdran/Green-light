package com.erp.greenlight.repositories;

import com.erp.greenlight.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalesInvoiceRepo extends JpaRepository<SalesInvoice, Long> {

    @Query("SELECT SUM(s.moneyForAccount) FROM SalesInvoice s WHERE s.account=:account")
    BigDecimal netSalesInvoicesForCustomer(Account account);


    @Query(value = "SELECT * FROM sales_invoices WHERE is_approved = 1 ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<SalesInvoice> getLast5SalesApproved();



    @Query("SELECT MONTH(s.invoiceDate) as month, COUNT(s) as count FROM SalesInvoice s WHERE s.isApproved GROUP BY MONTH(s.invoiceDate) ORDER BY MONTH(s.invoiceDate)")
    List<Object[]> findSalesInvoiceCountsByMonth();

    @Query("SELECT SUM(s.moneyForAccount) FROM SalesInvoice s WHERE  s.account=:account")
    BigDecimal getSumOfMoneyByAccount(Account account);

    @Query(value = "SELECT COUNT(*) FROM sales_invoices s WHERE s.customer =:customer ", nativeQuery = true)
    int getSalesCountByCustomerId(Long customer);

    @Query(value = "SELECT COUNT(*) FROM sales_invoices s WHERE  s.customer =:customer AND s.invoice_date <=:dateTo AND s.invoice_date >=:dateFrom", nativeQuery = true)
    int getSalesCountByCustomerIdOnPeriod(@Param("customer") Long customer, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

    @Query("SELECT SUM(s.moneyForAccount) FROM SalesInvoice s WHERE s.account=:account AND s.invoiceDate <=:dateTo AND s.invoiceDate >=:dateFrom")
    BigDecimal getSumOfMoneyByAccountOnPeriod(@Param("account") Account account, @Param("dateFrom")LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);


    @Query("SELECT s FROM SalesInvoice s WHERE s.customer=:customer")
    List<SalesInvoice> findAllByCustomer(Customer customer);

    @Query("SELECT s FROM SalesInvoice s WHERE s.customer=:customer AND s.invoiceDate <=:dateTo AND s.invoiceDate >=:dateFrom")
    List<SalesInvoice> findAllByCustomerOnPeriod(Customer customer, @Param("dateFrom")LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);


}
