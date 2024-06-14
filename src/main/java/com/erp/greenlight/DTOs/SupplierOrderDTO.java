package com.erp.greenlight.DTOs;

import com.erp.greenlight.models.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SupplierOrderDTO {
    private String docNo;


   // private String orderDate;

    private Long supplier;

    private String notes;

    private Byte pillType=1;

    private Integer store;


}
