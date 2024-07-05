package com.erp.greenlight.DTOs;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SavePriceInvoiceDetailsDTO {

    private Long id;
    private Long orderId;
    private BigDecimal unitPrice;
    private Long invItemCard;
    private Long uom;
    private BigDecimal ItemQuantity;

}
