package com.erp.greenlight.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "admins_treasuries")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminTreasury {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Long adminId;

    @Column(nullable = false)
    private Integer treasuriesId;

    private Boolean active=true;

    @Column(nullable = false)
    private Integer addedBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private Integer updatedBy;

    @Column(updatable = false)
    private LocalDateTime updatedAt;

//    @Column(nullable = false)
//    private Integer comCode;

    @Column(nullable = false)
    private LocalDate date;
}

