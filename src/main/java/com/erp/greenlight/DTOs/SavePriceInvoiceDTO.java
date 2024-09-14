package com.erp.greenlight.DTOs;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SavePriceInvoiceDTO {

    private Long id;
    private LocalDate invoiceDate;

    private String customer;

    private String note;
    private boolean taxIncluded;
    private LocalDate offerDuration;
    private String deliveryLocation;
}
