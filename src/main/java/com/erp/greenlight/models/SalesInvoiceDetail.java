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
import java.util.List;

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
    @ManyToOne()
    @JoinColumn(name = "sales_invoice",referencedColumnName = "id")
    private SalesInvoice salesInvoice;
//    @Column(name = "sales_invoices_auto_serial", nullable = false)
//    private Long salesInvoiceAutoSerial;  // Likely a foreign key referencing a SalesInvoice

    @ManyToMany()
    @JoinColumn(name = "store_id",referencedColumnName = "id")
    private List<Store> stores;  // Likely a foreign key referencing a Store

    @Column(name = "sales_item_type", nullable = false)
    private Byte salesItemType;  // Byte for tinyint(1) - sales item type

    @OneToOne()
    @JoinColumn(name = "item_code",referencedColumnName = "id") //inv item f k
    private InvItemCard item;  // Likely a foreign key referencing an Item table
    @ManyToOne()
    @JoinColumn(name = "uom_id",referencedColumnName = "id")
    private InvUom uom;  // Likely a foreign key referencing a Unit of Measure table

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
    private Boolean isParentUom;  // Byte for tinyint(1) - main or retail UoM

//    @Column(name = "com_code", nullable = false)
//    private Integer comCode;

    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

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


    @Column(name = "production_date")
    private LocalDate productionDate = LocalDate.now();

    @Column(name = "expire_date")
    private LocalDate expireDate = LocalDate.now();

    @Column(name = "date")
    private LocalDate date;


}

