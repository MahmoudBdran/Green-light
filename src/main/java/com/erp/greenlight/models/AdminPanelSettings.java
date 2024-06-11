package com.erp.greenlight.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin_panel_settings")
@EntityListeners({AuditingEntityListener.class})
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
    private Long employeesParentAccountNumber;


    @ManyToOne()
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    @CreatedBy
    private Admin addedBy;

    @ManyToOne()
    @JoinColumn(name = "updated_by",referencedColumnName = "id")
    @LastModifiedBy
    private Admin updatedBy;

    @Column(nullable = false)
    private LocalDateTime createdAt=LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String notes;
}

