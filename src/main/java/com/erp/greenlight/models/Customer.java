package com.erp.greenlight.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerCode;

    @Column(nullable = false, length = 225)
    private String name;

    @Column(nullable = false)
    private Long accountNumber;

    @Column(nullable = false)
    private int startBalanceStatus; // Consider using an enum for status

    @Column(nullable = false)
    private BigDecimal startBalance;

    @Column(nullable = false)
    private BigDecimal currentBalance = BigDecimal.ZERO; // Set default to 0.00 using BigDecimal.ZERO

    @Column(length = 225)
    private String notes;

    @Column(nullable = false)
    private int addedBy;

    private Integer updatedBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt=null;

    @Column(nullable = false)
    private boolean active=true;

    @Column(nullable = false)
    private int comCode;

    @Column(nullable = false)
    private LocalDate date;

    @Column(length = 250)
    private String address;

    @Column(length = 50)
    private String phones;

}

