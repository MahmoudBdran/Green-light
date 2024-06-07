package com.erp.greenlight.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin_panel_settings")
public class AdminPanelSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String systemName;

    @Column(nullable = false)
    private String photo;

    @Column(nullable = false)
    private Boolean active;

    private String generalAlert;

    @Column(nullable = false)
    private String address;

    private String phone;

    @Column(nullable = false)
    private Long customerParentAccountNumber;

    @Column(nullable = false)
    private Long suppliersParentAccountNumber;

    @Column(nullable = false)
    private Long delegateParentAccountNumber;

    @Column(nullable = false)
    private Long employeesParentAccountNumber;

    private Integer addedBy;

    private Integer updatedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

//    @Column(nullable = false)
//    private Integer comCode;

    private String notes;
}

