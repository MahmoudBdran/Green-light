package com.erp.greenlight.repositories;

import com.erp.greenlight.models.SalesInvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalesInvoiceDetailsRepo extends JpaRepository<SalesInvoiceDetail, Long> {

    Optional<SalesInvoiceDetail> findBySalesInvoiceIdAndItemIdAndUomId(Long orderId, Long invItemCardId, Long uomId);

    List<SalesInvoiceDetail> findBySalesInvoiceId(Long id);
}
