package com.erp.greenlight.repositories;

import com.erp.greenlight.models.SalesInvoicesReturnDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalesInvoiceReturnDetailsRepo extends JpaRepository<SalesInvoicesReturnDetails, Long> {

    Optional<SalesInvoicesReturnDetails> findBySalesInvoiceReturnIdAndItemIdAndUomId(Long orderId, Long invItemCardId, Long uomId);

    List<SalesInvoicesReturnDetails> findBySalesInvoiceReturnId(Long id);
}
