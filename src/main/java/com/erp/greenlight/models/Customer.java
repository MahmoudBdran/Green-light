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
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
@EntityListeners({AuditingEntityListener.class})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerCode;

    @Column(  length = 225)
    private String name;

    private Long accountNumber;

    private int startBalanceStatus; // Consider using an enum for status

    private BigDecimal startBalance;

    private BigDecimal currentBalance = BigDecimal.ZERO; // Set default to 0.00 using BigDecimal.ZERO

    @Column(length = 225)
    private String notes;

    @ManyToOne()
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    @CreatedBy
    private Admin addedBy;

    private Integer updatedBy;

    @Column(nullable = false)
    private LocalDateTime createdAt=LocalDateTime.now();

    private LocalDateTime updatedAt;

    private boolean active=true;


    private LocalDate date = LocalDate.now();

    @Column(length = 250)
    private String address;

    @Column(length = 50)
    private String phones;

}

