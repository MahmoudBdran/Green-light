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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "sales_invoices_return")  // Optional: Specify table name if it differs from the class name
@EntityListeners({AuditingEntityListener.class})
public class SalesInvoiceReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Long id;

    @Column(name = "return_type", nullable = false)
    private Byte returnType=1;  // Byte for tinyint(1) - return type (invoice-based or general)


    @OneToMany(mappedBy = "salesInvoiceReturn")
    private List<SalesInvoicesReturnDetails> salesInvoicesReturnDetails;
    @Column(name = "invoice_date", nullable = false)
    @CreatedDate
    private LocalDate invoiceDate;

    @Column(name = "is_has_customer", nullable = false)
    private Boolean isHasCustomer;  // Boolean for tinyint(1)

    @ManyToOne()
    @JoinColumn(name = "customer",referencedColumnName = "id")
    private Customer customer;


    @Column(name = "is_approved", nullable = false)  // Default value set in SQL
    private Boolean isApproved;  // Boolean for tinyint(1)


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

    @ManyToOne()
    @JoinColumn(name = "account_number",referencedColumnName = "id")
    private Account accountNumber; //relation with account

    @Column(name = "money_for_account", precision = 10, scale = 2)
    private BigDecimal moneyForAccount;  // Decimal for money for account

    @Column(name = "pill_type")
    private Byte pillType;  // Byte for tinyint(1) - invoice type (cash or credit)

    @Column(name = "what_paid", nullable = false, precision = 10, scale = 2)
    private BigDecimal whatPaid;  // Decimal for amount paid

    @Column(name = "what_remain", nullable = false, precision = 10, scale = 2)
    private BigDecimal whatRemain;  // Decimal for remaining amount

    @Column(name = "treasuries_transactions_id")
    private Long treasuriesTransactionsId=1L;  // Likely a foreign key for a TreasuryTransaction table

    @Column(name = "customer_balance_befor", precision = 10, scale = 2)
    private BigDecimal customerBalanceBefore;  // Decimal for customer balance before invoice

    @Column(name = "customer_balance_after", precision = 10, scale = 2)
    private BigDecimal customerBalanceAfter;

    @CreatedBy
    @ManyToOne()
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    private Admin addedBy;
    @Column(name = "created_at")
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
    @JoinColumn(name = "approved_by",referencedColumnName = "id")
    private Admin approvedBy;


    public SalesInvoiceReturn(Long id) {
        this.id = id;
    }
}

