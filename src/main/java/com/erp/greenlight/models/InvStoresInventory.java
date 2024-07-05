package com.erp.greenlight.models;
import com.erp.greenlight.enums.InventoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class InvStoresInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "inventory_type")
    private InventoryType inventoryType;

    @Column(name = "is_closed")
    private Boolean isClosed;

    @Column(name = "does_add_all_items")
    private Boolean doesAddAllItems;



    @Column(name = "total_cost_batches")
    private BigDecimal totalCostBatches;

    @Column(name = "notes")
    private String notes;

    @ManyToOne()
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    @CreatedBy
    private Admin addedBy;

    @ManyToOne()
    @JoinColumn(name = "updated_by",referencedColumnName = "id")
    @LastModifiedBy
    private Admin updatedBy;


    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "cloased_by")
    private Integer closedBy;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;
}

