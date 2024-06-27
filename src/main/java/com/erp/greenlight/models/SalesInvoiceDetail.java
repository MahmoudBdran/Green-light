package com.erp.greenlight.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table(name = "sales_invoices_details")
@EntityListeners({AuditingEntityListener.class}) // Optional: Specify table name if it differs from the class name
public class SalesInvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_invoice",referencedColumnName = "id")
    @JsonIgnore
    private SalesInvoice salesInvoice;

    @ManyToOne()
    @JoinColumn(name = "store_id",referencedColumnName = "id")
    private Store store;  // Likely a foreign key referencing a Store

    @Column(name = "sales_item_type", nullable = false)
    private Byte salesItemType=1;  // Byte for tinyint(1) - sales item type

    @OneToOne()
    @JoinColumn(name = "item_code",referencedColumnName = "id") //inv item f k
    private InvItemCard item;  // Likely a foreign key referencing an Item table


    @ManyToOne()
    @JoinColumn(name = "uom_id",referencedColumnName = "id")
    private InvUom uom;  // Likely a foreign key referencing a Unit of Measure table

    @ManyToOne()
    @JoinColumn(name = "batch_id",referencedColumnName = "id")
    private InvItemCardBatch batch;  // Likely a foreign key referencing a Batch table

    @Column(name = "quantity", nullable = false, precision = 10, scale = 4)
    private BigDecimal quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "is_normal_orOther", nullable = false)
    private Byte isNormalOrOther=1;  // Byte for tinyint(1) - item type (normal, bonus, etc.)

    @Column(name = "isparentuom", nullable = false)
    private Boolean isParentUom;  // Byte for tinyint(1) - main or retail UoM


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


}

