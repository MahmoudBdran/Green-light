package com.erp.greenlight.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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

    @ManyToOne()
    @JoinColumn(name = "account_type",referencedColumnName = "id")
    private AccountType accountType;

    @Column(name="is_parent")
    private Boolean isParent;

    @ManyToOne
    @JoinColumn(name = "parent_account_number")
    private Account parentAccount;

    @OneToMany(mappedBy = "parentAccount", cascade = CascadeType.ALL)
    private Set<Account> childAccounts = new HashSet<>();

    @Column(name="account_number")
    private Long accountNumber;

    private int startBalanceStatus;
    private BigDecimal startBalance;
    private BigDecimal currentBalance = BigDecimal.ZERO;

    @Column(name = "other_table_FK")
    private Long otherTableFk;

    @Column(length = 225)
    private String notes;

    private int addedBy;
    private Integer updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean active=true;

    public void addChildAccount(Account child) {
        childAccounts.add(child);
        child.setParentAccount(this);
    }

    public void removeChildAccount(Account child) {
        childAccounts.remove(child);
        child.setParentAccount(null);
    }

    public Account(Long id) {
        this.id = id;
    }
}

