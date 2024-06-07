package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data
public class Delegate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "delegate_code")
    private Long delegateCode;

    @Column(name = "name")
    private String name;

    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "start_balance_status")
    private Integer startBalanceStatus;

    @Column(name = "start_balance")
    private BigDecimal startBalance;

    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @Column(name = "notes")
    private String notes;

    @Column(name = "added_by")
    private Integer addedBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "active")
    private Boolean active;

//    @Column(name = "com_code")
//    private Integer comCode;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "phones")
    private String phones;

    @Column(name = "address")
    private String address;

    @Column(name = "percent_type")
    private Integer percentType;

    @Column(name = "percent_collect_commission")
    private BigDecimal percentCollectCommission;

    @Column(name = "percent_salaes_commission_kataei")
    private BigDecimal percentSalaesCommissionKataei;

    @Column(name = "percent_salaes_commission_nosjomla")
    private BigDecimal percentSalaesCommissionNosjomla;

    @Column(name = "percent_salaes_commission_jomla")
    private BigDecimal percentSalaesCommissionJomla;
}

