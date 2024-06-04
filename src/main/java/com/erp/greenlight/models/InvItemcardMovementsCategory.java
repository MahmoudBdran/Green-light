package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class InvItemcardMovementsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
}
