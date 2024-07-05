package com.erp.greenlight.DTOs;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SaveSarfPermissionDTO {

    private Long id;
    private LocalDate permissionDate;

    private Long customer;

    private String notes;

    private String receiverName;


}
