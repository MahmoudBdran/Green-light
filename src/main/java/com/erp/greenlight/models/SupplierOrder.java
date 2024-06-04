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
@Table(name = "suppliers_with_orders")
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
    private LocalDate orderDate;

    @Column(name = "suuplier_code", nullable = false)
    private Long supplierCode;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;  // Boolean for tinyint(1)

    @Column(name = "com_code", nullable = false)
    private Integer comCode;

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

    @Column(name = "account_number", nullable = false)
    private Long accountNumber;

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

    @Column(name = "added_by", nullable = false)
    private Integer addedBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "approved_by")
    private Integer approvedBy;

    // Getters and setters (optional but recommended)
    // ...
}
