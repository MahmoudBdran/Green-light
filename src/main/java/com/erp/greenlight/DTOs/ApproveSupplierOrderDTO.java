package com.erp.greenlight.DTOs;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApproveSupplierOrderDTO {

    private Long orderId;

    private BigDecimal taxPercent;
    private BigDecimal taxValue;


    private Byte pillType;
    private Byte discountType;
    private BigDecimal discountPercent;
    private BigDecimal discountValue;
    private BigDecimal whatPaid;
    private BigDecimal whatRemain;
    private String notes;
}
/*
{
    "orderId": 3,
    "discountType": 1,
    "discountPercent": 0,
    "discountValue": 0,
    "pillType": 1,
    "whatPaid": 12,
    "whatRemain": 20,
    "notes": "sdfsdf"
}

*/