package com.erp.greenlight.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "suppliers_categories")
@EntityListeners({AuditingEntityListener.class})
public class SupplierCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Integer id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

   @LastModifiedBy
    @ManyToOne()
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    private Admin addedBy;

    @LastModifiedBy
    @ManyToOne()
    @JoinColumn(name = "updated_by",referencedColumnName = "id")
    private Admin updatedBy;

    @Column(name = "active", nullable = false)
    private Boolean active;

    public SupplierCategory(Integer id) {
        this.id = id;
    }
}
