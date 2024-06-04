package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_type")
    private Integer orderType;

    @Column(name = "auto_serial")
    private Long autoSerial;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "com_code")
    private Integer comCode;

    @Column(name = "notes")
    private String notes;

    @Column(name = "total_services")
    private BigDecimal totalServices;

    @Column(name = "discount_type")
    private Integer discountType; // Consider using an enum for discount type

    @Column(name = "discount_percent")
    private BigDecimal discountPercent;

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "tax_percent")
    private BigDecimal taxPercent;

    @Column(name = "tax_value")
    private BigDecimal taxValue;

    @Column(name = "total_befor_discount")
    private BigDecimal totalBeforeDiscount;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "is_account_number")
    private Boolean isAccountNumber;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "money_for_account")
    private BigDecimal moneyForAccount;

    @Column(name = "pill_type")
    private Integer pillType; // Consider using an enum for pill type

    @Column(name = "what_paid")
    private BigDecimal whatPaid;

    @Column(name = "what_remain")
    private BigDecimal whatRemain;

    @Column(name = "treasuries_transactions_id")
    private Long treasuriesTransactionsId;

    @Column(name = "added_by")
    private Integer addedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "approved_by")
    private Integer approvedBy;
}

