package com.erp.greenlight.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_invoices_return_details")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesInvoicesReturnDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sales_invoices_auto_serial", nullable = false)
    private Long salesInvoicesAutoSerial;

    @Column(nullable = false)
    private Integer storeId;

    @Column(name = "sales_item_type", nullable = false)
    private Integer salesItemType;

    @Column(nullable = false)
    private Long itemCode;

    @Column(name = "uom_id", nullable = false)
    private Integer uomId;

    @Column(name = "batch_auto_serial")
    private Long batchAutoSerial;

    @Column(precision = 10, scale = 4)
    private BigDecimal quantity= BigDecimal.valueOf(0.0000);

    @Column(name = "unit_cost_price", precision = 10, scale = 2)
    private BigDecimal unitCostPrice;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice=BigDecimal.valueOf(0.00);

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice=BigDecimal.valueOf(0.00);

    @Column(name = "is_normal_orOther", nullable = false)
    private Integer isNormalOrOther;

    @Column(name = "isparentuom", nullable = false)
    private Integer isparentuom;

//    @Column(nullable = false)
//    private Integer comCode;

    @Column(nullable = false)
    private LocalDate invoiceDate;

    @Column(nullable = false)
    private Integer addedBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private Integer updatedBy;

    @Column(updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "production_date")
    private LocalDate productionDate;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @Column(nullable = false)
    private LocalDate date;
}

