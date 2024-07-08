package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.Customer;
import com.erp.greenlight.models.SalesInvoice;
import com.erp.greenlight.models.SalesInvoiceReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalesInvoiceReturnRepo extends JpaRepository<SalesInvoiceReturn, Long> {

    @Query("SELECT SUM(s.moneyForAccount) FROM SalesInvoiceReturn s WHERE  s.account=:account")
    BigDecimal getSumOfMoneyByAccount(Account account);

    @Query(value = "SELECT COUNT(*) FROM sales_invoices_return s WHERE s.customer =:customer ", nativeQuery = true)
    int getSalesReturnCountByCustomerId(Long customer);

    @Query(value = "SELECT COUNT(*) FROM sales_invoices_return s WHERE  s.customer =:customer AND s.invoice_date <=:dateTo AND s.invoice_date >=:dateFrom", nativeQuery = true)
    int getSalesReturnCountByCustomerIdOnPeriod(@Param("customer") Long customer, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

    @Query("SELECT SUM(s.moneyForAccount) FROM SalesInvoiceReturn s WHERE s.account=:account AND s.invoiceDate <=:dateTo AND s.invoiceDate >=:dateFrom")
    BigDecimal getSumOfMoneyByAccountOnPeriod(@Param("account") Account account, @Param("dateFrom")LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);


    @Query("SELECT s FROM SalesInvoiceReturn s WHERE s.customer=:customer")
    List<SalesInvoiceReturn> findAllByCustomer(Customer customer);

    @Query("SELECT s FROM SalesInvoiceReturn s WHERE s.customer=:customer AND s.invoiceDate <=:dateTo AND s.invoiceDate >=:dateFrom")
    List<SalesInvoiceReturn> findAllByCustomerOnPeriod(Customer customer, @Param("dateFrom")LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
}
