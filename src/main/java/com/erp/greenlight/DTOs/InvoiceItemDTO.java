package com.erp.greenlight.DTOs;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InvoiceItemDTO {
    private Long OrderItemId;
    private Long orderId;
    private Long invItemId;
    private Long uomId;
    private BigDecimal deliveredQuantity;
    private BigDecimal unitPrice;

}
