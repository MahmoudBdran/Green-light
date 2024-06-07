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

@Entity
@Data
@Table(name = "suppliers")
@EntityListeners({AuditingEntityListener.class})
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Long id;


    @ManyToOne()
    @JoinColumn(name = "suppliers_categories_id",referencedColumnName = "id")
    private SupplierCategory suppliersCategoriesId;

    @Column(name = "name", nullable = false, length = 225)  // Specify length for varchar columns
    private String name;

    @OneToOne()
    @JoinColumn(name = "account_number",referencedColumnName = "id")
    private Account accountNumber;

    @Column(name = "start_balance_status", nullable = false)
    private int startBalanceStatus;  // Byte for tinyint

    @Column(name = "start_balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal startBalance;

    @Column(name = "current_balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal currentBalance;

    @Column(name = "notes", length = 225)
    private String notes;

    @CreatedBy
    @ManyToOne()
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    private Admin addedBy;

    @LastModifiedBy
    @ManyToOne()
    @JoinColumn(name = "updated_by",referencedColumnName = "id")
    private Admin updatedBy;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "active", nullable = false)
    private Boolean isActive;  // Boolean for tinyint(1)

//    @Column(name = "com_code", nullable = false)
//    private Integer comCode;

    @Column(name = "date", nullable = false)
    private LocalDate date;  // LocalDate for date column

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "phones", length = 50)
    private String phones;

    // Getters and setters (optional but recommended)
    // ...
}

