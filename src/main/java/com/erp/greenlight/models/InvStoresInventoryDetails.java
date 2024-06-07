package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data
public class InvStoresInventoryDetails {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "inv_stores_inventory_auto_serial")
    private Long invStoresInventoryAutoSerial;

    @Column(name = "item_code")
    private Long itemCode;

    @Column(name = "inv_uoms_id")
    private Integer invUomsId;

    @Column(name = "batch_auto_serial")
    private Long batchAutoSerial;

    @Column(name = "old_quantity")
    private BigDecimal oldQuantity;

    @Column(name = "new_quantity")
    private BigDecimal newQuantity;

    @Column(name = "diffrent_quantity")
    private BigDecimal diffrentQuantity;

    @Column(name = "unit_cost_price")
    private BigDecimal unitCostPrice;

    @Column(name = "total_cost_price")
    private BigDecimal totalCostPrice;

    @Column(name = "production_date")
    private LocalDate productionDate;

    @Column(name = "expired_date")
    private LocalDate expiredDate;

    @Column(name = "notes")
    private String notes;

    @Column(name = "is_closed")
    private Boolean isClosed;

    @Column(name = "added_by")
    private Integer addedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "cloased_by")
    private Integer cloasedBy;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

//    @Column(name = "com_code")
//    private Integer comCode;
}
