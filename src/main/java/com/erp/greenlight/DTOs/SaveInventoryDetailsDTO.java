package com.erp.greenlight.DTOs;

import com.erp.greenlight.enums.InventoryType;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SaveInventoryDetailsDTO {

    private Long id;

    private Long inventoryId;
    private Boolean doesAddAllItems;
    private LocalDate date;
    private InventoryType inventoryType;
    private Integer store;
    private String notes;


}
