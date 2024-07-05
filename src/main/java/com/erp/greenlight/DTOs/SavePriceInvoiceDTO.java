package com.erp.greenlight.DTOs;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SavePriceInvoiceDTO {

    private Long id;
    private LocalDate invoiceDate;

    private Long customer;
    private String notes;
    private String receiverName;
}
