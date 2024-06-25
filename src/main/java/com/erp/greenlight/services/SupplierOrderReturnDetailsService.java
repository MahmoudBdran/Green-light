package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.InvoiceItemDTO;
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
    SupplierOrderReturnDetailsRepo supplierOrderReturnDetailsRepo;
    @Autowired
    SupplierOrderReturnRepo supplierOrderReturnRepo;
    @Autowired
    InvUomRepo invUomRepo;
    @Autowired
    InvItemCardRepo invItemCardRepo;
    public List<SupplierOrderReturnDetails> findByOrderReturnId(Long id){

        return supplierOrderReturnDetailsRepo.findByOrderReturnId(id);
    }

    @Transactional
    public List<SupplierOrderReturnDetails> saveItemInOrderReturn(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SupplierOrderReturnDetails supplierOrderReturnDetails = new SupplierOrderReturnDetails();
        //map from DTO to the Original Entity

        return mapSupplierOrderDetailsDtoToSupplierOrderDetails(parsedInvoiceItemDto, supplierOrderReturnDetails);

    }
    @Transactional
    public List<SupplierOrderReturnDetails> updateItemInOrderReturn(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SupplierOrderReturnDetails supplierOrderReturnDetails = supplierOrderReturnDetailsRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get();
        //map from DTO to the Original Entity
        return mapSupplierOrderDetailsDtoToSupplierOrderDetails(parsedInvoiceItemDto, supplierOrderReturnDetails);
        //
    }
    @Transactional
    public SupplierOrderReturnDetails updateItemBeingInsertedAgain(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SupplierOrderReturnDetails supplierOrderReturnDetails = supplierOrderReturnDetailsRepo.findByOrderReturnIdAndInvItemCard_IdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(),parsedInvoiceItemDto.getUom()).orElseThrow();
        supplierOrderReturnDetails.setDeliveredQuantity(supplierOrderReturnDetails.getDeliveredQuantity().add(parsedInvoiceItemDto.getDeliveredQuantity()));
        supplierOrderReturnDetails.setTotalPrice(supplierOrderReturnDetails.getUnitPrice().multiply(supplierOrderReturnDetails.getDeliveredQuantity()));
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself

        float totalPrice=0;
        for(SupplierOrderReturnDetails details : supplierOrderReturnDetailsRepo.findByOrderReturnId(parsedInvoiceItemDto.getOrderId())){
            System.out.println(details.getTotalPrice());
            totalPrice+=details.getTotalPrice().floatValue();
        }

        System.out.println("totalPrice : "+totalPrice);
        SupplierOrderReturn supplierOrderReturn = supplierOrderReturnRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        supplierOrderReturn.setTotalCost(BigDecimal.valueOf(totalPrice));
        System.out.println("totalPrice in obj : "+supplierOrderReturn.getTotalCost());
        supplierOrderReturn.setTotalBeforeDiscount(supplierOrderReturn.getTotalCost().add(supplierOrderReturn.getTaxValue()==null? BigDecimal.ZERO:supplierOrderReturn.getTaxValue()));
        System.out.println("totalPrice : "+supplierOrderReturn.getTotalBeforeDiscount());
        System.out.println("totalPrice : "+supplierOrderReturn.getTaxValue());
        supplierOrderReturnRepo.save(supplierOrderReturn);
        totalPrice=0;
        return supplierOrderReturnDetailsRepo.save(supplierOrderReturnDetails);
    }

    @Transactional
    public  List<SupplierOrderReturnDetails> deleteItemFromSupplierOrderReturn(Long id) {
        SupplierOrderReturnDetails supplierOrderReturnDetails = supplierOrderReturnDetailsRepo.findById(id).orElseThrow();
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself
        supplierOrderReturnDetailsRepo.deleteById(id);
        float totalPrice=0;
        for(SupplierOrderReturnDetails details : supplierOrderReturnDetailsRepo.findByOrderReturnId(supplierOrderReturnDetails.getOrderReturn().getId())){
            totalPrice+=details.getTotalPrice().floatValue();
        }
        SupplierOrderReturn supplierOrderReturn = supplierOrderReturnRepo.findById(supplierOrderReturnDetails.getOrderReturn().getId()).orElseThrow();
        supplierOrderReturn.setTotalCost(BigDecimal.valueOf(totalPrice));
        supplierOrderReturn.setTotalBeforeDiscount(supplierOrderReturn.getTotalCost().add(supplierOrderReturn.getTaxValue()==null? BigDecimal.ZERO:supplierOrderReturn.getTaxValue()));
        totalPrice=0;
         supplierOrderReturnRepo.save(supplierOrderReturn);
        return supplierOrderReturn.getSupplierOrderReturnDetailsItems();
    }
    @Transactional
    public List<SupplierOrderReturnDetails> mapSupplierOrderDetailsDtoToSupplierOrderDetails(InvoiceItemDTO parsedInvoiceItemDto,SupplierOrderReturnDetails supplierOrderReturnDetails) {


        supplierOrderReturnDetails.setOrderReturn(new SupplierOrderReturn(parsedInvoiceItemDto.getOrderId()));
        supplierOrderReturnDetails.setInvItemCard(new InvItemCard(parsedInvoiceItemDto.getInvItemCard()));
        supplierOrderReturnDetails.setUom(new InvUom(parsedInvoiceItemDto.getUom()));
        supplierOrderReturnDetails.setDeliveredQuantity(parsedInvoiceItemDto.getDeliveredQuantity());
        supplierOrderReturnDetails.setUnitPrice(parsedInvoiceItemDto.getUnitPrice());
        supplierOrderReturnDetails.setTotalPrice(parsedInvoiceItemDto.getUnitPrice().multiply(parsedInvoiceItemDto.getDeliveredQuantity()==null?BigDecimal.ONE:parsedInvoiceItemDto.getDeliveredQuantity()));
        supplierOrderReturnDetails.setOrderType(supplierOrderReturnRepo.findById(parsedInvoiceItemDto.getOrderId()).get().getOrderType());
        supplierOrderReturnDetails.setIsParentUom(invUomRepo.findById(parsedInvoiceItemDto.getUom()).get().isMaster());
        supplierOrderReturnDetails.setInvItemCard(new InvItemCard(parsedInvoiceItemDto.getInvItemCard()));
        supplierOrderReturnDetails.setBatchAutoSerial(1L);
        supplierOrderReturnDetails.setItemCardType((byte) invItemCardRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get().getItemType());
        //saving the supplierOrderReturnDetails in the DB.

        SupplierOrderReturnDetails savedSupplierOrderReturnDetails= supplierOrderReturnDetailsRepo.save(supplierOrderReturnDetails);
        System.out.println("saved savedSupplierOrderReturnDetails service method");
        //updating the Order itself with the updates.
        SupplierOrderReturn supplierOrderReturn = supplierOrderReturnRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        supplierOrderReturn.setTotalCost(supplierOrderReturnRepo.findById(parsedInvoiceItemDto.getOrderId()).get().getTotalCost().add(supplierOrderReturnDetails.getTotalPrice()==null?BigDecimal.ZERO:supplierOrderReturnDetails.getTotalPrice()));
        supplierOrderReturn.setTotalBeforeDiscount(supplierOrderReturn.getTotalCost().add(supplierOrderReturn.getTaxValue()==null? BigDecimal.ZERO:supplierOrderReturn.getTaxValue()));
        supplierOrderReturnRepo.save(supplierOrderReturn);


      return supplierOrderReturnRepo.findById(supplierOrderReturnDetails.getOrderReturn().getId()).orElseThrow().getSupplierOrderReturnDetailsItems();
    }
    @Transactional
    public boolean checkOrderDetailsItemIsApproved(Long id){
        SupplierOrderReturnDetails supplierOrderReturnDetails = supplierOrderReturnDetailsRepo.findById(id).orElseThrow();
        SupplierOrderReturn supplierOrderReturn= supplierOrderReturnRepo.findById(supplierOrderReturnDetails.getOrderReturn().getId()).orElseThrow();
        return supplierOrderReturn.getIsApproved();
    }
    public boolean checkItemInOrderOrNot(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {
        System.out.println("entered checkItemInORderOrNot method");
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getOrderId());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getUom());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getInvItemCard());
        Optional<SupplierOrderReturnDetails> supplierOrderReturnDetails= supplierOrderReturnDetailsRepo.findByOrderReturnIdAndInvItemCard_IdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(), parsedInvoiceItemDto.getUom());
        if (supplierOrderReturnDetails.isEmpty()){
            return false;
        }else{
            System.out.println(supplierOrderReturnDetails.get().getOrderReturn().getId());
            System.out.println(supplierOrderReturnDetails.get().getInvItemCard().getId());
            System.out.println(supplierOrderReturnDetails.get().getUom().getId());
            return true;
        }
    }
}

