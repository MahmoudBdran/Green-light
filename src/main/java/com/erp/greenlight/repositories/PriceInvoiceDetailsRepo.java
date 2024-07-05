package com.erp.greenlight.repositories;

import com.erp.greenlight.models.PriceInvoiceDetail;
import com.erp.greenlight.models.SalesInvoiceDetail;
import com.erp.greenlight.models.SupplierOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceInvoiceDetailsRepo extends JpaRepository<PriceInvoiceDetail, Long> {
    List<PriceInvoiceDetail> findByPriceInvoiceId(Long id);
    Optional<PriceInvoiceDetail> findByPriceInvoiceIdAndItemIdAndUomId(Long orderId, Long invItemCardId, Long uomId);

}
