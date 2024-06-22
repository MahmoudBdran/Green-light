package com.erp.greenlight.repositories;

import com.erp.greenlight.models.InvItemCard;
import com.erp.greenlight.models.InvItemCardBatch;
import com.erp.greenlight.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InvItemCardBatchRepo extends JpaRepository<InvItemCardBatch,Long> {



    @Query("SELECT SUM(b.quantity) FROM InvItemCardBatch b WHERE item=:item")
    BigDecimal getQuantityBeforeMove(InvItemCard item);

    @Query("SELECT SUM(b.quantity) FROM InvItemCardBatch b WHERE item=:item AND store=:store")
    BigDecimal getQuantityBeforeMoveCurrentStore(InvItemCard item, Store store);



    List<InvItemCardBatch> findAllByStoreIdAndItemIdAndInvUomIdAndUnitCostPriceAndQuantity(Integer storeId, Long itemId, Long invUomId, BigDecimal unitCostPrice, BigDecimal quantity);
}
