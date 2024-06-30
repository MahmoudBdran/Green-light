package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.ReturnInvoiceItemDTO;
import com.erp.greenlight.enums.SupplierOrderType;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierOrderReturnDetailsService {
    @Autowired
    SupplierOrderDetailsRepo supplierOrderDetailsRepo;
    @Autowired
    SupplierOrderRepo supplierOrderRepo;
    @Autowired
    InvUomRepo invUomRepo;
    @Autowired
    InvItemCardRepo invItemCardRepo;
    
    public List<SupplierOrderDetails> findByOrderReturnId(Long id){
        return supplierOrderDetailsRepo.findByOrderId(id);
    }

    @Transactional
    public List<SupplierOrderDetails> saveItemInOrderReturn(ReturnInvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {
        SupplierOrderDetails details = new SupplierOrderDetails();
        return mapSupplierOrderDetailsDtoToSupplierOrderDetails(parsedInvoiceItemDto, details);
    }
    
    @Transactional
    public List<SupplierOrderDetails> updateItemInOrderReturn(ReturnInvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {
        SupplierOrderDetails supplierOrderReturnDetails = supplierOrderDetailsRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get();
        return mapSupplierOrderDetailsDtoToSupplierOrderDetails(parsedInvoiceItemDto, supplierOrderReturnDetails);
    }
    
    @Transactional
    public SupplierOrderDetails updateItemBeingInsertedAgain(ReturnInvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SupplierOrderDetails supplierOrderReturnDetails = supplierOrderDetailsRepo.findByOrderIdAndInvItemCard_IdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(),parsedInvoiceItemDto.getUom()).orElseThrow();
        supplierOrderReturnDetails.setDeliveredQuantity(supplierOrderReturnDetails.getDeliveredQuantity().add(parsedInvoiceItemDto.getItemQuantity()));
        supplierOrderReturnDetails.setTotalPrice(supplierOrderReturnDetails.getUnitPrice().multiply(supplierOrderReturnDetails.getDeliveredQuantity()));
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself

        float totalPrice=0;
        for(SupplierOrderDetails details : supplierOrderDetailsRepo.findByOrderId(parsedInvoiceItemDto.getOrderId())){
            System.out.println(details.getTotalPrice());
            totalPrice+=details.getTotalPrice().floatValue();
        }

        System.out.println("totalPrice : "+totalPrice);
        SupplierOrder supplierOrderReturn = supplierOrderRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        supplierOrderReturn.setTotalCost(BigDecimal.valueOf(totalPrice));
        System.out.println("totalPrice in obj : "+supplierOrderReturn.getTotalCost());
        supplierOrderReturn.setTotalBeforeDiscount(supplierOrderReturn.getTotalCost().add(supplierOrderReturn.getTaxValue()==null? BigDecimal.ZERO:supplierOrderReturn.getTaxValue()));
        System.out.println("totalPrice : "+supplierOrderReturn.getTotalBeforeDiscount());
        System.out.println("totalPrice : "+supplierOrderReturn.getTaxValue());
        supplierOrderRepo.save(supplierOrderReturn);
        totalPrice=0;
        return supplierOrderDetailsRepo.save(supplierOrderReturnDetails);
    }

    @Transactional
    public  List<SupplierOrderDetails> deleteItemFromSupplierOrderReturn(Long id) {
        SupplierOrderDetails supplierOrderReturnDetails = supplierOrderDetailsRepo.findById(id).orElseThrow();
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself
        supplierOrderDetailsRepo.deleteById(id);
        float totalPrice=0;
        for(SupplierOrderDetails details : supplierOrderDetailsRepo.findByOrderId(supplierOrderReturnDetails.getOrder().getId())){
            totalPrice+=details.getTotalPrice().floatValue();
        }
        SupplierOrder supplierOrderReturn = supplierOrderRepo.findById(supplierOrderReturnDetails.getOrder().getId()).orElseThrow();
        supplierOrderReturn.setTotalCost(BigDecimal.valueOf(totalPrice));
        supplierOrderReturn.setTotalBeforeDiscount(supplierOrderReturn.getTotalCost().add(supplierOrderReturn.getTaxValue()==null? BigDecimal.ZERO:supplierOrderReturn.getTaxValue()));
        totalPrice=0;
         supplierOrderRepo.save(supplierOrderReturn);
        return supplierOrderReturn.getSupplierOrderDetailsItems();
    }
    @Transactional
    public List<SupplierOrderDetails> mapSupplierOrderDetailsDtoToSupplierOrderDetails(ReturnInvoiceItemDTO parsedInvoiceItemDto,SupplierOrderDetails supplierOrderReturnDetails) {


        supplierOrderReturnDetails.setOrder(new SupplierOrder(parsedInvoiceItemDto.getOrderId()));
        supplierOrderReturnDetails.setInvItemCard(new InvItemCard(parsedInvoiceItemDto.getInvItemCard()));
        supplierOrderReturnDetails.setUom(new InvUom(parsedInvoiceItemDto.getUom()));
        supplierOrderReturnDetails.setDeliveredQuantity(parsedInvoiceItemDto.getItemQuantity());
        supplierOrderReturnDetails.setUnitPrice(parsedInvoiceItemDto.getUnitPrice());
        supplierOrderReturnDetails.setTotalPrice(parsedInvoiceItemDto.getUnitPrice().multiply(parsedInvoiceItemDto.getItemQuantity()==null?BigDecimal.ONE:parsedInvoiceItemDto.getItemQuantity()));
        supplierOrderReturnDetails.setOrderType(SupplierOrderType.RETURN_ON_GENERAL);
        supplierOrderReturnDetails.setIsParentUom(invUomRepo.findById(parsedInvoiceItemDto.getUom()).get().isMaster());
        supplierOrderReturnDetails.setInvItemCard(new InvItemCard(parsedInvoiceItemDto.getInvItemCard()));
        supplierOrderReturnDetails.setBatchAutoSerial(1L);
        supplierOrderReturnDetails.setItemCardType((byte) invItemCardRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get().getItemType());
        //saving the supplierOrderReturnDetails in the DB.

        SupplierOrderDetails savedSupplierOrderDetails= supplierOrderDetailsRepo.save(supplierOrderReturnDetails);
        System.out.println("saved savedSupplierOrderDetails service method");
        //updating the Order itself with the updates.
        SupplierOrder supplierOrderReturn = supplierOrderRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        supplierOrderReturn.setTotalCost(supplierOrderRepo.findById(parsedInvoiceItemDto.getOrderId()).get().getTotalCost().add(supplierOrderReturnDetails.getTotalPrice()==null?BigDecimal.ZERO:supplierOrderReturnDetails.getTotalPrice()));
        supplierOrderReturn.setTotalBeforeDiscount(supplierOrderReturn.getTotalCost().add(supplierOrderReturn.getTaxValue()==null? BigDecimal.ZERO:supplierOrderReturn.getTaxValue()));
        supplierOrderRepo.save(supplierOrderReturn);


      return supplierOrderRepo.findById(supplierOrderReturnDetails.getOrder().getId()).orElseThrow().getSupplierOrderDetailsItems();
    }
    @Transactional
    public boolean checkOrderDetailsItemIsApproved(Long id){
        SupplierOrderDetails supplierOrderReturnDetails = supplierOrderDetailsRepo.findById(id).orElseThrow();
        SupplierOrder supplierOrderReturn= supplierOrderRepo.findById(supplierOrderReturnDetails.getOrder().getId()).orElseThrow();
        return supplierOrderReturn.getIsApproved();
    }
    public boolean checkItemInOrderOrNot(ReturnInvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {
        System.out.println("entered checkItemInORderOrNot method");
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getOrderId());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getUom());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getInvItemCard());
        Optional<SupplierOrderDetails> supplierOrderReturnDetails= supplierOrderDetailsRepo.findByOrderIdAndInvItemCard_IdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(), parsedInvoiceItemDto.getUom());
        if (supplierOrderReturnDetails.isEmpty()){
            return false;
        }else{
            System.out.println(supplierOrderReturnDetails.get().getOrder().getId());
            System.out.println(supplierOrderReturnDetails.get().getInvItemCard().getId());
            System.out.println(supplierOrderReturnDetails.get().getUom().getId());
            return true;
        }
    }
}

