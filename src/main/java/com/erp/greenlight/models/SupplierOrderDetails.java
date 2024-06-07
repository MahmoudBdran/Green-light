package com.erp.greenlight.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "suppliers_with_orders_details")
public class SupplierOrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Long id;

    @Column(name = "order_id", nullable = false)
    @ManyToOne()
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private SupplierOrder order_id;  // Foreign key referencing SupplierOrder

    @Column(name = "order_type", nullable = false)

    private Byte orderType;  // Byte for tinyint(1)

    @Column(name = "com_code", nullable = false)
    private Integer comCode;

    @Column(name = "deliverd_quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal deliveredQuantity;

    @Column(name = "uom_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "uom_id",referencedColumnName = "id")
    private InvUom uomId;  // Likely a foreign key for a Unit of Measurement table

    @Column(name = "isparentuom", nullable = false)
    private Boolean isParentUom;  // Boolean for tinyint(1)

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "order_date", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="order_date",referencedColumnName = "order_date")
    private SupplierOrder orderDate;

    @Column(name = "added_by", nullable = false)
    @CreatedBy
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Admin addedBy;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    @LastModifiedBy
    @JoinColumn(name = "updated_by",referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Admin updatedBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "item_code", nullable = false)
    private Long itemCode;  // Likely a foreign key for an Items table

    @Column(name = "batch_auto_serial")
    private Long batchAutoSerial;  // Potentially a foreign key for a Batch table

    @Column(name = "production_date")
    private LocalDate productionDate;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @Column(name = "item_card_type", nullable = false)
    private Byte itemCardType;  // Byte for tinyint(1)

    // Getters and setters (optional but recommended)
    // ...
}

