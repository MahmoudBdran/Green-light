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
@Table(name = "sales_invoices_details")  // Optional: Specify table name if it differs from the class name
public class SalesInvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Long id;

    @Column(name = "sales_invoices_auto_serial", nullable = false)
    private Long salesInvoiceAutoSerial;  // Likely a foreign key referencing a SalesInvoice

    @Column(name = "store_id", nullable = false)
    private Integer storeId;  // Likely a foreign key referencing a Store

    @Column(name = "sales_item_type", nullable = false)
    private Byte salesItemType;  // Byte for tinyint(1) - sales item type

    @Column(name = "item_code", nullable = false)
    private Long itemCode;  // Likely a foreign key referencing an Item table

    @Column(name = "uom_id", nullable = false)
    private Integer uomId;  // Likely a foreign key referencing a Unit of Measure table

    @Column(name = "batch_auto_serial")
    private Long batchAutoSerial;  // Likely a foreign key referencing a Batch table

    @Column(name = "quantity", nullable = false, precision = 10, scale = 4)
    private BigDecimal quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "is_normal_orOther", nullable = false)
    private Byte isNormalOrOther;  // Byte for tinyint(1) - item type (normal, bonus, etc.)

    @Column(name = "isparentuom", nullable = false)
    private Byte isParentUom;  // Byte for tinyint(1) - main or retail UoM

//    @Column(name = "com_code", nullable = false)
//    private Integer comCode;

    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    @Column(name = "added_by", nullable = false)
    private Integer addedBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "production_date")
    private LocalDate productionDate;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @Column(name = "date", nullable = false)
    private LocalDate date;


}

