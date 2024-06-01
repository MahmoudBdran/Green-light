package com.erp.greenlight.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inv_itemcard")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvItemcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name="item_code")
    private Long itemCode;

    @Column(nullable = false, length = 50)
    private String barcode;

    @Column(nullable = false, length = 225)
    private String name;

    @Column(nullable = false,name = "item_type")
    private int itemType; // Use an enum for 'واحد  مخزني - اتنين استهلاكي - ثلاثه عهده'

    @Column(nullable = false,name = "inv_itemcard_categories_id")
    private int invItemcardCategoriesId;
    @Column(name = "parent_inv_itemcard_id",length = 20)
    private Long parentInvItemcardId;

    @Column(nullable = false,name = "does_has_retailunit")
    private boolean doesHasRetailunit;
    @Column(name = "retail_uom_id",length = 11)
    private Integer retailUomId;

    @Column(nullable = false,name = "uom_id")
    private int uomId;
    @Column(name="retail_uom_quntToParent")
    private BigDecimal retailUomQuntToParent;

    @Column(nullable = false,name = "added_by")
    private int addedBy;

    @Column(nullable = false,name = "created_at")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer updatedBy;

    @Column(nullable = false)
    private boolean active=true;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int comCode;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal nosGomlaPrice;

    @Column(nullable = false)
    private BigDecimal gomlaPrice;

    private BigDecimal priceRetail;

    private BigDecimal nosGomlaPriceRetail;

    private BigDecimal gomlaPriceRetail;

    @Column(nullable = false)
    private BigDecimal costPrice;

    private BigDecimal costPriceRetail;

    @Column(nullable = false)
    private boolean hasFixcedPrice;

    private BigDecimal allQUENTITY;

    private BigDecimal QUENTITY;

    private BigDecimal QUENTITYRetail;

    private BigDecimal QUENTITYAllRetails;

    private String photo;

}