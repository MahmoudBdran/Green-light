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
@Table(name = "sales_invoices")
public class SalesInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "sales_matrial_types",referencedColumnName = "id")
    private SalesMaterialType salesMaterialType;

//    @Column(name = "auto_serial", nullable = false)
//    private Long autoSerial;

    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    @Column(name = "is_has_customer", nullable = false)
    private Boolean isHasCustomer;

    @ManyToOne()
    @JoinColumn(name = "customer",referencedColumnName = "id")
    private Customer customer;


    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;


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
    private Long accountNumber; //relation with account

    @Column(name = "money_for_account", precision = 10, scale = 2)
    private BigDecimal moneyForAccount;

    @OneToMany(mappedBy = "salesInvoice")
    private List<SalesInvoiceDetail> salesInvoiceDetails;

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


    @Column(name = "sales_item_type", nullable = false)
    private Byte salesItemTyp=1;  // Byte for tinyint(1) - sales item type

    public SalesInvoice(Long id) {
        this.id = id;
    }
}
