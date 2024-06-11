package com.erp.greenlight.models;

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
import java.util.List;

@Entity
@Data
@Table(name = "suppliers_with_orders")
@EntityListeners({AuditingEntityListener.class})
public class SupplierOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Long id;

    @Column(name = "order_type", nullable = false)
    private Byte orderType;  // Byte for tinyint(1)

    @Column(name = "auto_serial", nullable = false)
    private Long autoSerial;

    @Column(name = "DOC_NO", length = 25)
    private String docNo;

    @Column(name = "order_date", nullable = false)
    @CreatedDate
    private LocalDate orderDate;

    @OneToOne()
    @JoinColumn(name = "supplier_id",referencedColumnName = "id")
    private Supplier supplier;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;  // Boolean for tinyint(1)


    @OneToMany(mappedBy = "order")
    private List<SupplierOrderDetails> supplierOrderDetailsItems;

    @Column(name = "notes", length = 225)
    private String notes;

    @Column(name = "discount_type")
    private Byte discountType;  // Byte for tinyint(1)

    @Column(name = "discount_percent", precision = 10, scale = 2)
    private BigDecimal discountPercent;

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "tax_percent", precision = 10, scale = 2)
    private BigDecimal taxPercent;

    @Column(name = "total_cost_items", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCostItems;

    @Column(name = "tax_value", precision = 10, scale = 2)
    private BigDecimal taxValue;

    @Column(name = "total_befor_discount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalBeforeDiscount;

    @Column(name = "total_cost", precision = 10, scale = 2)
    private BigDecimal totalCost;

    @OneToOne()
    @JoinColumn(name = "account_number",referencedColumnName = "id")
    private Account account;

    @Column(name = "money_for_account", precision = 10, scale = 2)
    private BigDecimal moneyForAccount;

    @Column(name = "pill_type", nullable = false)
    private Byte pillType;  // Byte for tinyint(1)

    @Column(name = "what_paid", precision = 10, scale = 2)
    private BigDecimal whatPaid;

    @Column(name = "what_remain", precision = 10, scale = 2)
    private BigDecimal whatRemain;

    @Column(name = "treasuries_transactions_id")
    private Long treasuriesTransactionsId;

    @Column(name = "Supplier_balance_befor", precision = 10, scale = 2)
    private BigDecimal supplierBalanceBefore;

    @Column(name = "Supplier_balance_after", precision = 10, scale = 2)
    private BigDecimal supplierBalanceAfter;

    @CreatedBy
    @ManyToOne()
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    private Admin addedBy;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @ManyToOne()
    @JoinColumn(name = "updated_by",referencedColumnName = "id")
    private Admin updatedBy;

    @ManyToOne()
    @JoinColumn(name = "store_id",referencedColumnName = "id")
    private Store store;

    @ManyToOne()
    @JoinColumn(name = "approved_by",referencedColumnName = "id")
    private Admin approvedBy;

    // Getters and setters (optional but recommended)
    // ...
}
