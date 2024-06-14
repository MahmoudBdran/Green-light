package com.erp.greenlight.mappers;

import com.erp.greenlight.DTOs.SupplierOrderDTO;
import com.erp.greenlight.models.SupplierOrder;
import org.mapstruct.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

public class SupplierOrderMapper {

    public static SupplierOrder toEntity(SupplierOrderDTO dto){
        SupplierOrder entity=new SupplierOrder();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static SupplierOrderDTO toDto(SupplierOrder entity){
        SupplierOrderDTO dto = new SupplierOrderDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
