package com.erp.greenlight.models;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "suppliers_with_orders_auto_serial", nullable = false)
    private Long supplierOrderAutoSerial;  // Foreign key referencing SupplierOrder

    @Column(name = "order_type", nullable = false)
    private Byte orderType;  // Byte for tinyint(1)

    @Column(name = "com_code", nullable = false)
    private Integer comCode;

    @Column(name = "deliverd_quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal deliveredQuantity;

    @Column(name = "uom_id", nullable = false)
    private Integer uomId;  // Likely a foreign key for a Unit of Measurement table

    @Column(name = "isparentuom", nullable = false)
    private Boolean isParentUom;  // Boolean for tinyint(1)

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "added_by", nullable = false)
    private Integer addedBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
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

