package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class InvItemcardMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "InvItemcard_movements_category_id")
    private InvItemcardMovementsCategory invItemcardMovementsCategory;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private InvItemCard item;

    @ManyToOne
    @JoinColumn(name = "items_movements_type_id")
    private InvItemcardMovementsType invItemcardMovementsType;

    @Column(name = "byan")
    private String byan;

    @Column(name = "quantity_befor_movement")
    private String quantityBeforMovement;

    @Column(name = "quantity_after_move")
    private String quantityAfterMove;

    @Column(name = "quantity_befor_move_store")
    private String quantityBeforMoveStore;

    @Column(name = "quantity_after_move_store")
    private String quantityAfterMoveStore;
}

