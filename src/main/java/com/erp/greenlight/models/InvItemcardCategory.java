package com.erp.greenlight.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private LocalDateTime createdAt;


    private LocalDateTime updatedAt=null;

    @Column(nullable = false)
    private int addedBy;

    private Integer updatedBy;

    @Column(nullable = false)
    private int comCode;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean active=true;

}
