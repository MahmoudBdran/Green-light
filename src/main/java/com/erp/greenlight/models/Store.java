package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data
@Table(name = "stores")
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
    @LastModifiedBy
    private LocalDateTime updatedAt;

    @CreatedBy
    @ManyToOne()
    @JoinColumn(name ="added_by",referencedColumnName = "id")
    private Admin addedBy;

    @LastModifiedBy
    @ManyToOne()
    @JoinColumn(name ="updated_by",referencedColumnName = "id")
    private Admin updatedBy;

//    @Column(name = "com_code", nullable = false)
//    private Integer comCode;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "active", nullable = false)
    private Boolean isActive;

}

