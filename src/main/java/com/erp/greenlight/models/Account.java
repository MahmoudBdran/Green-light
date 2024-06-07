package com.erp.greenlight.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
@EntityListeners({AuditingEntityListener.class})
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
    @JsonIgnore
    //@JsonIgnoreProperties({"parentAccount","childAccounts"})
    private Account parentAccount;

    @OneToMany(mappedBy = "parentAccount", cascade = CascadeType.ALL)
    @JsonIgnore
    //@JsonIgnoreProperties({"parentAccount","childAccounts"})
    private Set<Account> childAccounts = new HashSet<>();


    private int startBalanceStatus;
    private BigDecimal startBalance;
    private BigDecimal currentBalance = BigDecimal.ZERO;


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


    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
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

