package com.erp.greenlight.models;
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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "stores")
@EntityListeners({AuditingEntityListener.class})
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "phones", length = 100)
    private String phones;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    @ManyToOne()
    @JoinColumn(name ="added_by",referencedColumnName = "id")
    private Admin addedBy;

    @LastModifiedBy
    @ManyToOne()
    @JoinColumn(name ="updated_by",referencedColumnName = "id")
    private Admin updatedBy;


    @Column(name = "active", nullable = false)
    private Boolean active;

    public Store(Integer id) {
        this.id = id;
    }
}

