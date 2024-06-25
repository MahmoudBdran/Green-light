package com.erp.greenlight.repositories;

import com.erp.greenlight.models.SupplierOrderDetails;
import com.erp.greenlight.models.SupplierOrderReturnDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierOrderReturnDetailsRepo extends JpaRepository<SupplierOrderReturnDetails, Long> {

    Optional<SupplierOrderReturnDetails> findByOrderReturnIdAndInvItemCard_IdAndUomId(Long orderId, Long invItemCardId, Long uomId);

    List<SupplierOrderReturnDetails>findByOrderReturnId(Long id);
}
