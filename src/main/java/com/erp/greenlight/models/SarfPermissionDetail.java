package com.erp.greenlight.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "sarf_permission_details")
@EntityListeners({AuditingEntityListener.class}) // Optional: Specify table name if it differs from the class name
public class SarfPermissionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sarf_permission_id",referencedColumnName = "id")
    @JsonIgnore
    private SarfPermission sarfPermission;

    @ManyToOne()
    @JoinColumn(name = "store_id",referencedColumnName = "id")
    private Store store;  // Likely a foreign key referencing a Store


    @ManyToOne()
    @JoinColumn(name = "item_code",referencedColumnName = "id") //inv item f k
    private InvItemCard item;  // Likely a foreign key referencing an Item table


    @ManyToOne()
    @JoinColumn(name = "uom_id",referencedColumnName = "id")
    private InvUom uom;  // Likely a foreign key referencing a Unit of Measure table

    @Column(name = "quantity", nullable = false, precision = 10, scale = 4)
    private BigDecimal quantity;



    @CreatedBy
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Admin addedBy;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    @JoinColumn(name = "updated_by",referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Admin updatedBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;


}

