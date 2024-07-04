package com.erp.greenlight.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SaveSarfPermissionDTO {

    private Long id;
    private String date;

    private Long customer;

    private String notes;
private Integer store;


}
