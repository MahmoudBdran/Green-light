package com.erp.greenlight.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "admins_shifts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminShift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long shiftCode;

    @Column(nullable = false)
    private Integer adminId;

    @Column(nullable = false)
    private Integer treasuriesId;

    @Column(name = "treasuries_balnce_in_shift_start", nullable = false, precision = 10, scale = 2)
    private BigDecimal treasuriesBalanceInShiftStart=BigDecimal.valueOf(0.00);

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(updatable = false)
    private LocalDateTime endDate;

    private Boolean isFinished=false;

    private Boolean isDeliveredAndReview=false;

    @Column(name = "delivered_to_admin_id")
    private Integer deliveredToAdminId;

    @Column(name = "delivered_to_admin_sift_id")
    private Long deliveredToAdminShiftId;

    @Column(name = "delivered_to_treasuries_id")
    private Integer deliveredToTreasuriesId;

    @Column(name = "money_should_deviled", precision = 10, scale = 2)
    private BigDecimal moneyShouldDeviled;

    @Column(name = "what_realy_delivered", precision = 10, scale = 2)
    private BigDecimal whatRealyDelivered;

    @Column(name = "money_state")
    private Integer moneyState;

    @Column(name = "money_state_value", precision = 10, scale = 2)
    private BigDecimal moneyStateValue;

    @Column(name = "receive_type")
    private Integer receiveType;

    @Column(name = "review_receive_date")
    private LocalDateTime reviewReceiveDate;

    @Column(name = "treasuries_transactions_id")
    private Long treasuriesTransactionsId;

    @Column(nullable = false)
    private Integer addedBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(length = 100)
    private String notes;

//    @Column(nullable = false)
//    private Integer comCode;

    @Column(nullable = false)
    private LocalDate date;

    @Column(updatable = false)
    private Integer updatedBy;

    @Column(updatable = false)
    private LocalDateTime updatedAt;
}

