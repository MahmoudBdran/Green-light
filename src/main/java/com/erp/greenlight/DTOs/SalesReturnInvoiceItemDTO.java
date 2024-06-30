package com.erp.greenlight.DTOs;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SalesReturnInvoiceItemDTO {
    private Long id;
    private Long orderId;
    private BigDecimal unitPrice;
    private BigDecimal unitCostPrice;
    private Long batch;
    private Long invItemCard;
    private Integer store;
    private Long uom;
    private BigDecimal ItemQuantity;
}
