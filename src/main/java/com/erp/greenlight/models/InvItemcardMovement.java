package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data
public class InvItemcardMovement {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "inv_itemcard_movements_categories")
    private Integer invItemcardMovementsCategories;

    @Column(name = "item_code")
    private Long itemCode;

    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "items_movements_types")
    private Integer itemsMovementsTypes;

    @Column(name = "FK_table")
    private Long fkTable;

    @Column(name = "FK_table_details")
    private Long fkTableDetails;

    @Column(name = "byan")
    private String byan;

    @Column(name = "quantity_befor_movement")
    private String quantityBeforMovement;

    @Column(name = "quantity_after_move")
    private String quantityAfterMove;

    @Column(name = "added_by")
    private Integer addedBy;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "com_code")
    private Integer comCode;

    @Column(name = "quantity_befor_move_store")
    private String quantityBeforMoveStore;

    @Column(name = "quantity_after_move_store")
    private String quantityAfterMoveStore;
}

