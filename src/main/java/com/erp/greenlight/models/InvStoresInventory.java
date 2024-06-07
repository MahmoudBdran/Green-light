package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data
public class InvStoresInventory {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "inventory_date")
    private LocalDate inventoryDate;

    @Column(name = "inventory_type")
    private Integer inventoryType;

    @Column(name = "auto_serial")
    private Long autoSerial;

    @Column(name = "is_closed")
    private Boolean isClosed;

    @Column(name = "total_cost_batches")
    private BigDecimal totalCostBatches;

    @Column(name = "notes")
    private String notes;

    @Column(name = "added_by")
    private Integer addedBy;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    @Column(name = "com_code")
//    private Integer comCode;

    @Column(name = "cloased_by")
    private Integer closedBy;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;
}

