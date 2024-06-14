package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.InvoiceItemDTO;
import com.erp.greenlight.DTOs.SupplierOrderDTO;
import com.erp.greenlight.mappers.SupplierOrderMapper;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.InvItemCardRepo;
import com.erp.greenlight.repositories.InvUomRepo;
import com.erp.greenlight.repositories.SupplierOrderDetailsRepo;
import com.erp.greenlight.repositories.SupplierOrderRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierOrderService {
    @Autowired
    SupplierOrderRepo supplierOrderRepo;
    @Autowired
    InvUomRepo invUomRepo;
    @Autowired
    InvItemCardRepo invItemCardRepo;
    @Autowired
    SupplierOrderDetailsRepo supplierOrderDetailsRepo;
     SupplierOrderMapper mapper;
    public List<SupplierOrder> getAllSupplierOrders(){
        return supplierOrderRepo.findAll();
    }
    public Optional<SupplierOrder> getSupplierOrderById( Long id){
        return Optional.of(supplierOrderRepo.findById(id).get());
    }
    public SupplierOrder saveSupplierOrder(SupplierOrderDTO supplierOrderDTO){
        System.out.println(supplierOrderDTO.getSupplier());
        System.out.println(supplierOrderDTO.getStore());
        System.out.println(supplierOrderDTO.getDocNo());
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setDocNo(supplierOrderDTO.getDocNo());
        supplierOrder.setSupplier(new Supplier(supplierOrderDTO.getSupplier()));
        supplierOrder.setStore(new Store(supplierOrderDTO.getStore()));
        supplierOrder.setNotes(supplierOrderDTO.getNotes());
        supplierOrder.setPillType(supplierOrderDTO.getPillType());
        //supplierOrder.setOrderDate(supplierOrder.getOrderDate());
        return supplierOrderRepo.save(supplierOrder);
    }

    public void deleteSupplierOrder( Long id){
        supplierOrderRepo.deleteById(id);
    }
    @Transactional
    public SupplierOrderDetails saveItemInOrder(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {
//        //map every one of this to the DTO
//
        System.out.println(parsedInvoiceItemDto.toString());
        System.out.println("entered saveItemInOrder service method");
        int adminId=1;
//
        //mapping the parsed Invoice Item DTO to SupplierOrderDetails object
        SupplierOrderDetails supplierOrderDetails = new SupplierOrderDetails();
        supplierOrderDetails.setOrder(new SupplierOrder(parsedInvoiceItemDto.getOrderId()));
        supplierOrderDetails.setInvItemCard(new InvItemCard(parsedInvoiceItemDto.getInvItemId()));
        supplierOrderDetails.setUom(new InvUom(parsedInvoiceItemDto.getUomId()));
        supplierOrderDetails.setDeliveredQuantity(parsedInvoiceItemDto.getDeliveredQuantity());
        supplierOrderDetails.setUnitPrice(parsedInvoiceItemDto.getUnitPrice());
        supplierOrderDetails.setTotalPrice(parsedInvoiceItemDto.getUnitPrice().multiply(parsedInvoiceItemDto.getDeliveredQuantity()==null?BigDecimal.ONE:parsedInvoiceItemDto.getDeliveredQuantity()));
        supplierOrderDetails.setAddedBy(new Admin(adminId));
        supplierOrderDetails.setUpdatedBy(new Admin(adminId));
        supplierOrderDetails.setOrderType(supplierOrderRepo.findById(parsedInvoiceItemDto.getOrderId()).get().getOrderType());
        supplierOrderDetails.setIsParentUom(invUomRepo.findById(parsedInvoiceItemDto.getUomId()).get().isMaster());
        supplierOrderDetails.setInvItemCard(new InvItemCard(parsedInvoiceItemDto.getInvItemId()));
        supplierOrderDetails.setBatchAutoSerial(1L);
        supplierOrderDetails.setItemCardType((byte) invItemCardRepo.findById(parsedInvoiceItemDto.getInvItemId()).get().getItemType());
        //saving the supplierOrderDetails in the DB.

        SupplierOrderDetails savedSupplierOrderDetails= supplierOrderDetailsRepo.save(supplierOrderDetails);

        System.out.println("saved savedSupplierOrderDetails service method");
        //updating the Order itself with the updates.
        SupplierOrder supplierOrder =supplierOrderRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        supplierOrder.setTotalCost(supplierOrderRepo.findById(parsedInvoiceItemDto.getOrderId()).get().getTotalCost().add(supplierOrderDetails.getTotalPrice()==null?BigDecimal.ZERO:supplierOrderDetails.getTotalPrice()));
        supplierOrder.setUpdatedBy(new Admin(adminId));
        supplierOrder.setTotalBeforeDiscount(supplierOrder.getTotalCost().add(supplierOrder.getTaxValue()==null? BigDecimal.ZERO:supplierOrder.getTaxValue()));
        supplierOrderRepo.save(supplierOrder);

        System.out.println("updated supplierOrder service method");
        return savedSupplierOrderDetails;
        //

    }
}

