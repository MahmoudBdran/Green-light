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
@Table(name = "sales_invoices")
public class SalesInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sales_matrial_types")
    private Integer salesMaterialType;

    @Column(name = "auto_serial", nullable = false)
    private Long autoSerial;

    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    @Column(name = "is_has_customer", nullable = false)
    private Boolean isHasCustomer;

    @Column(name = "customer_code")
    private Long customerCode;

    @Column(name = "delegate_code")
    private Long delegateCode;

    @Column(name = "delegate_commission_percent_type")
    private BigDecimal delegateCommissionPercentType;

    @Column(name = "delegate_commission_percent", nullable = false, precision = 10, scale = 2)
    private BigDecimal delegateCommissionPercent;

    @Column(name = "delegate_commission_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal delegateCommissionValue;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;  // Boolean for tinyint(1)

//    @Column(name = "com_code", nullable = false)
//    private Integer comCode;

    @Column(name = "notes", length = 225)
    private String notes;

    @Column(name = "discount_type")
    private Byte discountType;

    @Column(name = "discount_percent", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountPercent;

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "tax_percent", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxPercent;

    @Column(name = "total_cost_items", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCostItems;

    @Column(name = "tax_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxValue;

    @Column(name = "total_befor_discount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalBeforeDiscount;

    @Column(name = "total_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "money_for_account", precision = 10, scale = 2)
    private BigDecimal moneyForAccount;

    @Column(name = "pill_type")
    private Byte pillType;

    @Column(name = "what_paid", nullable = false, precision = 10, scale = 2)
    private BigDecimal whatPaid;  // Decimal for amount paid

    @Column(name = "what_remain", nullable = false, precision = 10, scale = 2)
    private BigDecimal whatRemain;  // Decimal for remaining amount

    @Column(name = "treasuries_transactions_id")
    private Long treasuriesTransactionsId;  // Likely a foreign key for a TreasuryTransaction table

    @Column(name = "customer_balance_befor", precision = 10, scale = 2)
    private BigDecimal customerBalanceBefore;  // Decimal for customer balance before invoice

    @Column(name = "customer_balance_after", precision = 10, scale = 2)
    private BigDecimal customerBalanceAfter;  // Decimal for customer balance after invoice

    @Column(name = "added_by", nullable = false)
    private Integer addedBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "approved_by")
    private Integer approvedBy;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "sales_item_type", nullable = false)
    private Byte salesItemType;  // Byte for tinyint(1) - sales item type


}
