package com.erp.greenlight.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "suppliers_with_orders_details")
@EntityListeners({AuditingEntityListener.class})
public class SupplierOrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    @JsonIgnore
    private SupplierOrder order;  // Foreign key referencing SupplierOrder


    @Column(name = "order_type", nullable = false)

    private Byte orderType;  // Byte for tinyint(1)

//    @Column(name = "com_code", nullable = false)
//    private Integer comCode;

    @Column(name = "deliverd_quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal deliveredQuantity;


    @OneToOne
    @JoinColumn(name = "uom_id", referencedColumnName = "id", nullable = false)
    private InvUom uom;  // Likely a foreign key for a Unit of Measurement table

    @Column(name = "isparentuom", nullable = false)
    private Boolean isParentUom;  // Boolean for tinyint(1)

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;


    @CreatedBy
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Admin addedBy;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    @JoinColumn(name = "updated_by",referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Admin updatedBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;


    @OneToOne
    @JoinColumn(name = "inv_item_id", referencedColumnName = "id")
    private InvItemCard invItemCard;  // Likely a foreign key for an Items table

    @Column(name = "batch_auto_serial")
    private Long batchAutoSerial;  // Potentially a foreign key for a Batch table

    @Column(name = "production_date")
    private LocalDate productionDate;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @Column(name = "item_card_type")
    private Byte itemCardType;  // Byte for tinyint(1)

    // Getters and setters (optional but recommended)
    // ...
}

