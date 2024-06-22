package com.erp.greenlight.models;

import lombok.*;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "customers")
@EntityListeners({AuditingEntityListener.class})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(  length = 225)
    private String name;

    @OneToOne
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    private Account account;

    private int startBalanceStatus; // Consider using an enum for status

    private BigDecimal startBalance;

    private BigDecimal currentBalance = BigDecimal.ZERO; // Set default to 0.00 using BigDecimal.ZERO

    @Column(length = 225)
    private String notes;

    @ManyToOne()
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    @CreatedBy
    private Admin addedBy;

    @ManyToOne()
    @JoinColumn(name = "updated_by",referencedColumnName = "id")
    @LastModifiedBy
    private Admin updatedBy;

    @Column(nullable = false)
    private LocalDateTime createdAt=LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean active;

    @Column(length = 250)
    private String address;

    @Column(length = 50)
    private String phones;

    public Customer(Long id) {
        this.id = id;
    }
}

