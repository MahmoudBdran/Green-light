package com.erp.greenlight.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 225)
    private String name;

//    @ManyToOne(fetch = FetchType.LAZY) // One-to-Many relationship with AccountType
//   // @JoinColumn(name = "account_type", nullable = false,referencedColumnName = "id") // Foreign key
//    @JoinColumn(name = "account_type" ,referencedColumnName = "id")
//
    @Column(nullable = false)
    private int accountType;

    @Column(nullable = false)
    private boolean isParent=false;

    @Column(name = "parent_account_number")
    private Long parentAccountNumber;

    @Column(nullable = false)
    private Long accountNumber;

    @Column(nullable = false)
    private int startBalanceStatus; // Consider using an enum for status

    @Column(nullable = false)
    private BigDecimal startBalance;

    @Column(nullable = false)
    private BigDecimal currentBalance = BigDecimal.ZERO; // Set default to 0.00 using BigDecimal.ZERO

    @Column(name = "other_table_FK")
    private Long otherTableFk;

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

}

