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
@Table(name = "sales_invoices_return")  // Optional: Specify table name if it differs from the class name
public class SalesInvoiceReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Long id;

    @Column(name = "return_type", nullable = false)
    private Byte returnType;  // Byte for tinyint(1) - return type (invoice-based or general)

    @Column(name = "sales_matrial_types")
    private Integer salesMaterialType;  // Likely an integer representing sales material type

    @Column(name = "auto_serial", nullable = false)
    private Long autoSerial;  // Bigint for large invoice codes

    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    @Column(name = "is_has_customer", nullable = false)
    private Boolean isHasCustomer;  // Boolean for tinyint(1)

    @Column(name = "customer_code")
    private Long customerCode;  // Likely a foreign key referencing a Customer table

    @Column(name = "delegate_code")
    private Long delegateCode;  // Likely a foreign key referencing an Employee or Delegate table

    @Column(name = "is_approved", nullable = false, insertable = false, updatable = false)  // Default value set in SQL
    private Boolean isApproved;  // Boolean for tinyint(1)

    @Column(name = "com_code", nullable = false)
    private Integer comCode;

    @Column(name = "notes", length = 225)
    private String notes;

    @Column(name = "discount_type")
    private Byte discountType;  // Byte for tinyint(1) - discount type (percentage or value)

    @Column(name = "discount_percent", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountPercent;  // Decimal for discount percentage

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;  // Decimal for discount amount

    @Column(name = "tax_percent", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxPercent;  // Decimal for tax percentage

    @Column(name = "total_cost_items", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCostItems;  // Decimal for total cost of items

    @Column(name = "tax_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxValue;  // Decimal for tax value

    @Column(name = "total_befor_discount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalBeforeDiscount;  // Decimal for total before discount

    @Column(name = "total_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;  // Decimal for total invoice cost

    @Column(name = "account_number")
    private Long accountNumber;  // Potential foreign key for an Accounts table

    @Column(name = "money_for_account", precision = 10, scale = 2)
    private BigDecimal moneyForAccount;  // Decimal for money for account

    @Column(name = "pill_type")
    private Byte pillType;  // Byte for tinyint(1) - invoice type (cash or credit)

    @Column(name = "what_paid", nullable = false, precision = 10, scale = 2)
    private BigDecimal whatPaid;  // Decimal for amount paid

    @Column(name = "what_remain", nullable = false, precision = 10, scale = 2)
    private BigDecimal whatRemain;  // Decimal for remaining amount

    @Column(name = "treasuries_transactions_id")
    private Long treasuriesTransactionsId;  // Likely a foreign key for a TreasuryTransaction table

    @Column(name = "customer_balance_befor", precision = 10, scale = 2)
    private BigDecimal customerBalanceBefore;  // Decimal for customer balance before invoice

    @Column(name = "customer_balance_after", precision = 10, scale = 2)
    private BigDecimal customerBalanceAfter;

    @Column(nullable = false)
    private Integer addedBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private LocalDateTime updatedAt;

    @Column(updatable = false)
    private Integer updatedBy;

    @Column(updatable = false)
    private Integer approvedBy;

    @Column(nullable = false)
    private LocalDate date;
}

