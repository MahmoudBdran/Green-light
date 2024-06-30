package com.erp.greenlight.DTOs;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SaveTreasuryTransactionDto {

    private LocalDate moveDate;
    private Long account;
    private Integer movType;
    private BigDecimal money;
    private String bayn;
}
