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
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setDocNo(supplierOrderDTO.getDocNo());
        supplierOrder.setSupplier(new Supplier(supplierOrderDTO.getSupplier()));
        supplierOrder.setStore(new Store(supplierOrderDTO.getStore()));
        supplierOrder.setNotes(supplierOrderDTO.getNotes());
        supplierOrder.setPillType(supplierOrderDTO.getPillType());
        //supplierOrder.setOrderDate(supplierOrder.getOrderDate());
        return supplierOrderRepo.save(supplierOrder);
    }

    public SupplierOrder updateSupplierOrder(SupplierOrder supplierOrder){
//        SupplierOrder supplierOrder = supplierOrderRepo.findById(supplierOrderDTO.getOrderId()).get();
//        supplierOrder.setDocNo(supplierOrderDTO.getDocNo());
//        supplierOrder.setSupplier(new Supplier(supplierOrderDTO.getSupplier()));
//        supplierOrder.setStore(new Store(supplierOrderDTO.getStore()));
//        supplierOrder.setNotes(supplierOrderDTO.getNotes());
//        supplierOrder.setPillType(supplierOrderDTO.getPillType());
        return supplierOrderRepo.save(supplierOrder);
    }

    public void deleteSupplierOrder( Long id){
        supplierOrderRepo.deleteById(id);
    }
    @Transactional
    public SupplierOrderDetails saveItemInOrder(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SupplierOrderDetails supplierOrderDetails = new SupplierOrderDetails();
        //map from DTO to the Original Entity

        return mapSupplierOrderDetailsDtoToSupplierOrderDetails(parsedInvoiceItemDto, supplierOrderDetails);

    }

    @Transactional
    public SupplierOrderDetails updateItemInOrder(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SupplierOrderDetails supplierOrderDetails = supplierOrderDetailsRepo.findById(parsedInvoiceItemDto.getOrderItemId()).get();
        //map from DTO to the Original Entity
       return mapSupplierOrderDetailsDtoToSupplierOrderDetails(parsedInvoiceItemDto, supplierOrderDetails);
        //

    }
    @Transactional
    public SupplierOrderDetails mapSupplierOrderDetailsDtoToSupplierOrderDetails(InvoiceItemDTO parsedInvoiceItemDto,SupplierOrderDetails supplierOrderDetails) {

        int adminId=1;
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

        return savedSupplierOrderDetails;
    }

    public boolean checkItemInOrderOrNot(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SupplierOrderDetails supplierOrderDetails=supplierOrderDetailsRepo.findByOrderIdAndInvItemCard_IdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemId(), parsedInvoiceItemDto.getUomId()).get();
        if (supplierOrderDetails==null){
            return false;
        }else{
            System.out.println(supplierOrderDetails.getOrder().getId());
            System.out.println(supplierOrderDetails.getInvItemCard().getId());
            System.out.println(supplierOrderDetails.getUom().getId());
            return true;
        }
    }

    @Transactional
    public SupplierOrderDetails updateItemBeingInsertedAgain(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {
        SupplierOrderDetails supplierOrderDetails = supplierOrderDetailsRepo.findByOrderIdAndInvItemCard_IdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemId(),parsedInvoiceItemDto.getUomId()).orElseThrow();
        supplierOrderDetails.setDeliveredQuantity(supplierOrderDetails.getDeliveredQuantity().add(parsedInvoiceItemDto.getDeliveredQuantity()));
        supplierOrderDetails.setTotalPrice(supplierOrderDetails.getUnitPrice().multiply(supplierOrderDetails.getDeliveredQuantity()));
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself

        float totalPrice=0;
        for(SupplierOrderDetails details : supplierOrderDetailsRepo.findByOrderId(parsedInvoiceItemDto.getOrderId())){
            System.out.println(details.getTotalPrice());
            totalPrice+=details.getTotalPrice().floatValue();
        }
        System.out.println("totalPrice : "+totalPrice);

        SupplierOrder supplierOrder =supplierOrderRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        supplierOrder.setTotalCost(BigDecimal.valueOf(totalPrice));
        System.out.println("totalPrice in obj : "+supplierOrder.getTotalCost());
        supplierOrder.setUpdatedBy(new Admin(1));
        supplierOrder.setTotalBeforeDiscount(supplierOrder.getTotalCost().add(supplierOrder.getTaxValue()==null? BigDecimal.ZERO:supplierOrder.getTaxValue()));
        System.out.println("totalPrice : "+supplierOrder.getTotalBeforeDiscount());
        System.out.println("totalPrice : "+supplierOrder.getTaxValue());
        supplierOrderRepo.save(supplierOrder);
        totalPrice=0;
        return supplierOrderDetailsRepo.save(supplierOrderDetails);
    }
}

