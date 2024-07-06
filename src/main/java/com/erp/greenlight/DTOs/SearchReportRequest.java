package com.erp.greenlight.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchReportRequest {

    private Long supplierId;
    private Long reportType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Boolean doesShowItems;

}
