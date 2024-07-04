package com.erp.greenlight.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_invoices_return_details")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class SalesInvoicesReturnDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "sales_invoice_return",referencedColumnName = "id")
    @JsonIgnore
    private SalesInvoiceReturn salesInvoiceReturn;


    @Column(name = "sales_item_type", nullable = false)
    private Byte salesItemType=1;
    @ManyToOne()
    @JoinColumn(name = "store_id",referencedColumnName = "id")
    private Store store;  // Likely a foreign key referencing a Store
    @ManyToOne()
    @JoinColumn(name = "item_Id",referencedColumnName = "id") //inv item f k
    private InvItemCard item;  // Likely a foreign key referencing an Item table

    @ManyToOne()
    @JoinColumn(name = "batch_id",referencedColumnName = "id")
    private InvItemCardBatch batch;  // Likely a foreign key referencing a Batch table

    @ManyToOne()
    @JoinColumn(name = "uom_id",referencedColumnName = "id")
    private InvUom uom;


    @Column(precision = 10, scale = 4)
    private BigDecimal quantity= BigDecimal.valueOf(0.0000);

    @Column(name = "unit_cost_price", precision = 10, scale = 2)
    private BigDecimal unitCostPrice;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice=BigDecimal.valueOf(0.00);

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice=BigDecimal.valueOf(0.00);


    @Column(name = "isparentuom", nullable = false)
    private Boolean isparentuom;

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
    private LocalDate productionDate;

    @Column(name = "expire_date")
    private LocalDate expireDate;
}

