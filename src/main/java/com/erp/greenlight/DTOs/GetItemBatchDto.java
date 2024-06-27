package com.erp.greenlight.DTOs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetItemBatchDto {

    private Long invItemId;
    private Integer storeId;
    private Long uomId;
}
