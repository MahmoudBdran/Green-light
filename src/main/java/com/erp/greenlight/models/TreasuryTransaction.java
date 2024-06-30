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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "treasuries_transactions")
@EntityListeners({AuditingEntityListener.class})

public class TreasuryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Long id;


    @Column(name = "isal_number", nullable = false)
    private Long isalNumber;  // Bigint for large transaction codes

    @Column(name = "shift_code", nullable = false)
    private Long shiftCode;  // Bigint for large shift codes

    @Column(name = "money", nullable = false, precision = 10, scale = 2)
    private BigDecimal money;  // Decimal for transaction amounts


    @ManyToOne
    @JoinColumn(name = "treasure_id", nullable = false)
    private Treasure treasure;  // Likely a foreign key referencing Treasury

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;  // Boolean for tinyint(1)

    @ManyToOne
    @JoinColumn(name = "mov_type", nullable = false)
    private MovType movType;  // Likely an enum or integer representing transaction type

    @Column(name = "move_date", nullable = false)
    private LocalDate moveDate;

    @Column(name = "the_foregin_key")
    private Long theForeignKey;  // Could be a foreign key to another table, depending on your data model

    @ManyToOne
    @JoinColumn(name = "account_number")
    private Account account;  // Potential foreign key for an Accounts table

    @Column(name = "is_account")
    private Boolean isAccount;  // Boolean for tinyint(1)

    @Column(name = "money_for_account", nullable = false, precision = 10, scale = 2)
    private BigDecimal moneyForAccount;

    @Column(name = "byan", nullable = false, length = 225)
    private String byan;  // Transaction description

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
}

