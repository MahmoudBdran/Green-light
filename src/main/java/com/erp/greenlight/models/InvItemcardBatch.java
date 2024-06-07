package com.erp.greenlight.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class InvItemcardBatch {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "item_code")
    private Integer itemCode;

    @Column(name = "inv_uoms_id")
    private Integer invUomsId;

    @Column(name = "unit_cost_price")
    private BigDecimal unitCostPrice;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "total_cost_price")
    private BigDecimal totalCostPrice;

    @Column(name = "production_date")
    private LocalDate productionDate;

    @Column(name = "expired_date")
    private LocalDate expiredDate;

//    @Column(name = "com_code")
//    private Integer comCode;

    @Column(name = "auto_serial")
    private Long autoSerial;

    @Column(name = "added_by")
    private Integer addedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "is_send_to_archived")
    private Boolean isSendToArchived;
}

