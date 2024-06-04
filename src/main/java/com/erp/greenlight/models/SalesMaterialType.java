package com.erp.greenlight.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_matrial_types")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesMaterialType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 250)
    private String name;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Integer addedBy;

    @Column(updatable = false)
    private Integer updatedBy;

    @Column(nullable = false)
    private Integer comCode;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Boolean active = true;
}

