package com.erp.greenlight.repositories;

import com.erp.greenlight.models.PriceInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PriceInvoiceRepo extends JpaRepository<PriceInvoice, Long> {




}
