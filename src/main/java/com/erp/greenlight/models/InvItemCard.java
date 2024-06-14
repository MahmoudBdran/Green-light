package com.erp.greenlight.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "inv_itemcard")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class InvItemCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column( length = 50)
    private String barcode;

    @Column(nullable = false, length = 225)
    private String name;

    @Column(nullable = false,name = "item_type")
    private int itemType; // Use an enum for 'واحد  مخزني - اتنين استهلاكي - ثلاثه عهده'
    
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "inv_item_category_id", referencedColumnName = "id", nullable = false)
    private InvItemcardCategory invItemCategory;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_inv_item_id",  referencedColumnName = "id")
    //@JsonIgnore
    private InvItemCard parentInvItemCard;

    @OneToMany(mappedBy = "parentInvItemCard", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<InvItemCard> childInvItems = new HashSet<>();
    
    
    @Column(nullable = false,name = "does_has_retailunit")
    private boolean doesHasRetailUnit;

    
    @ManyToOne
    @JoinColumn(name = "retail_uom_id", referencedColumnName = "id")
    private InvUom retailUom;

    @ManyToOne
    @JoinColumn(name = "uom_id", referencedColumnName = "id")
    private InvUom uom;
    
    @Column(name="retail_uom_quntToParent")
    private BigDecimal retailUomQuntToParent;
 
    @Column(nullable = false)
    private boolean active=true;
    
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

    public InvItemCard(Long id) {
        this.id = id;
    }
}