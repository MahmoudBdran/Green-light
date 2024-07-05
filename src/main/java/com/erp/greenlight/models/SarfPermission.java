package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "sarf_permissions")
@EntityListeners({AuditingEntityListener.class})
public class SarfPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate permissionDate;

    @ManyToOne()
    @JoinColumn(name = "customer",referencedColumnName = "id")
    private Customer customer;

    @Column(name = "receiver_name", length = 225)
    private String receiverName;

    @Column(name = "notes", length = 225)
    private String notes;

    @Column(name = "total_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;


    @OneToMany(mappedBy = "sarfPermission")
    private List<SarfPermissionDetail> detailsItems;


    @CreatedBy
    @ManyToOne()
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    private Admin addedBy;
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @ManyToOne()
    @JoinColumn(name = "updated_by",referencedColumnName = "id")
    private Admin updatedBy;


    public SarfPermission(Long id) {
        this.id = id;
    }
}
