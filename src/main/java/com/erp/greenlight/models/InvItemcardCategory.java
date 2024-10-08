package com.erp.greenlight.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inv_itemcard_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//جدول فئات الاصناف
public class InvItemcardCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 250)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    @CreatedBy
    private Admin addedBy;


    @ManyToOne()
    @JoinColumn(name = "updated_by",referencedColumnName = "id")
    @LastModifiedBy
    private Admin updatedBy;


    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean active=true;

}
