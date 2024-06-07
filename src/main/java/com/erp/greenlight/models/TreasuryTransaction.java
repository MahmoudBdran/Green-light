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
@Table(name = "treasuries_transactions")
public class TreasuryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Long id;

    @Column(name = "auto_serial", nullable = false)
    private Long autoSerial;  // Bigint for large transaction codes

    @Column(name = "isal_number", nullable = false)
    private Long isalNumber;  // Bigint for large transaction codes

    @Column(name = "shift_code", nullable = false)
    private Long shiftCode;  // Bigint for large shift codes

    @Column(name = "money", nullable = false, precision = 10, scale = 2)
    private BigDecimal money;  // Decimal for transaction amounts

    @Column(name = "treasuries_id", nullable = false)
    private Integer treasuryId;  // Likely a foreign key referencing Treasury

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;  // Boolean for tinyint(1)

    @Column(name = "mov_type", nullable = false)
    private Integer movType;  // Likely an enum or integer representing transaction type

    @Column(name = "move_date", nullable = false)
    private LocalDate moveDate;

    @Column(name = "the_foregin_key")
    private Long theForeignKey;  // Could be a foreign key to another table, depending on your data model

    @Column(name = "account_number")
    private Long accountNumber;  // Potential foreign key for an Accounts table

    @Column(name = "is_account")
    private Boolean isAccount;  // Boolean for tinyint(1)

    @Column(name = "money_for_account", nullable = false, precision = 10, scale = 2)
    private BigDecimal moneyForAccount;

    @Column(name = "byan", nullable = false, length = 225)
    private String byan;  // Transaction description

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "added_by", nullable = false)
    private Integer addedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

//    @Column(name = "com_code", nullable = false)
//    private Integer comCode;

    // Getters and setters (optional but recommended)
    // ...
}

