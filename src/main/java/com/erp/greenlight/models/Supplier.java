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
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Long id;

    @Column(name = "suuplier_code", nullable = false)
    private Long supplierCode;

    @Column(name = "suppliers_categories_id", nullable = false)
    private Integer suppliersCategoriesId;

    @Column(name = "name", nullable = false, length = 225)  // Specify length for varchar columns
    private String name;

    @Column(name = "account_number", nullable = false)
    private Long accountNumber;

    @Column(name = "start_balance_status", nullable = false)
    private Byte startBalanceStatus;  // Byte for tinyint

    @Column(name = "start_balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal startBalance;

    @Column(name = "current_balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal currentBalance;

    @Column(name = "notes", length = 225)
    private String notes;

    @Column(name = "added_by", nullable = false)
    private Integer addedBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "active", nullable = false)
    private Boolean isActive;  // Boolean for tinyint(1)

    @Column(name = "com_code", nullable = false)
    private Integer comCode;

    @Column(name = "date", nullable = false)
    private LocalDate date;  // LocalDate for date column

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "phones", length = 50)
    private String phones;

    // Getters and setters (optional but recommended)
    // ...
}

