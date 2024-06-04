package com.erp.greenlight.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "suppliers_categories")
public class SupplierCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Integer id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "added_by", nullable = false)
    private Integer addedBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "com_code", nullable = false)
    private Integer comCode;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "active", nullable = false)
    private Boolean isActive;  // Boolean for tinyint(1)

    // Getters and setters (optional but recommended)
    // ...
}
