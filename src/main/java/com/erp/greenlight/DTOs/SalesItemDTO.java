package com.erp.greenlight.DTOs;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SalesItemDTO {
    private Long id;
    private Long orderId;
    private Long invItemCard;
    private Long uom;
    private BigDecimal deliveredQuantity;
    private BigDecimal unitPrice;

}
