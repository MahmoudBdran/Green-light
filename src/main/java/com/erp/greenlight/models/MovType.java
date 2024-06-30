package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "in_screen")
    private Integer inScreen;

    @Column(name = "is_private_internal")
    private Boolean isPrivateInternal;

    public MovType(Integer id) {
        this.id = id;
    }
}

