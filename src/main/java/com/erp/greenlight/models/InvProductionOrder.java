package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data
public class InvProductionOrder {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "auto_serial")
    private Long autoSerial;

    @Column(name = "production_plane", columnDefinition = "text")
    private String productionPlan;

    @Column(name = "production_plan_date")
    private LocalDate productionPlanDate;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "added_by")
    private Integer addedBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "com_code")
    private Integer comCode;

    @Column(name = "approved_by")
    private Integer approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "is_closed")
    private Boolean isClosed;

    @Column(name = "closed_by")
    private Integer closedBy;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(name = "date")
    private LocalDate date;
}

