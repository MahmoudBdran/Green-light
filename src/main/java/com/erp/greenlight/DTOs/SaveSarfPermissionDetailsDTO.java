package com.erp.greenlight.DTOs;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SaveSarfPermissionDetailsDTO {



    private Long id;
    private Long permissionId;
    private Long invItemCard;
    private Integer store;
    private Long uom;
    private BigDecimal ItemQuantity;


}
