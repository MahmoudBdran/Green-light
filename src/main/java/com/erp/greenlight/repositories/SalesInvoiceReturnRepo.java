package com.erp.greenlight.repositories;

import com.erp.greenlight.models.SalesInvoice;
import com.erp.greenlight.models.SalesInvoiceReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesInvoiceReturnRepo extends JpaRepository<SalesInvoiceReturn, Long> {

}
