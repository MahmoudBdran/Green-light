package com.erp.greenlight.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SalesInvoiceDTO {
   // private String docNo;

    private Long orderId;
    private String orderDate;

    private Long customer;

    private String notes;

    private Byte pillType=1;

    private Integer store;


}
