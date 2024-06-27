package com.erp.greenlight.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvItemCardBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private InvItemCard item;


    @ManyToOne
    @JoinColumn(name = "inv_uom_id")
    private InvUom invUom;

    @Column(name = "unit_cost_price")
    private BigDecimal unitCostPrice;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "total_cost_price")
    private BigDecimal totalCostPrice;

    @Column(name = "production_date")
    private LocalDate productionDate = LocalDate.now();

    @Column(name = "expired_date")
    private LocalDate expiredDate = LocalDate.now();

    @Column(name = "is_send_to_archived")
    private Boolean isSendToArchived;


    public InvItemCardBatch(Long id) {
        this.id = id;
    }
}

