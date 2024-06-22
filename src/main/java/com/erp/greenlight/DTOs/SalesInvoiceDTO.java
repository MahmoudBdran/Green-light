package com.erp.greenlight.DTOs;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SalesInvoiceDTO {

    private Long orderId;
    private LocalDate date;

    private Long customer;

    private String notes;

    private Byte pillType;


}
