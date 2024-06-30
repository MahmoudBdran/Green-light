package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "treasuries")
@EntityListeners({AuditingEntityListener.class})
public class Treasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Integer id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "is_master", nullable = false)
    private Boolean isMaster;  // Boolean for tinyint(1)

    @Column(name = "last_isal_exhcange", nullable = false)
    private Long lastIsalExchange;  // Bigint for large numbers

    @Column(name = "last_isal_collect", nullable = false)
    private Long lastIsalCollect;  // Bigint for large numbers

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

    @Column(name = "active", nullable = false)
    private Boolean isActive;  // Boolean for tinyint(1)

    public Treasure(Integer id) {
        this.id = id;
    }
}

