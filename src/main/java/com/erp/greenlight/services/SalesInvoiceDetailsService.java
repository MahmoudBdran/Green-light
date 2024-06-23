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
public class SalesInvoiceDetailsService {
    @Autowired
    SalesInvoiceDetailsRepo salesInvoiceDetailsRepo;
    @Autowired
    SalesInvoiceRepo salesInvoiceRepo;
    @Autowired
    InvUomRepo invUomRepo;
    @Autowired
    InvItemCardRepo invItemCardRepo;
    public List<SalesInvoiceDetail> findBySalesInvoiceId(Long id){

        return salesInvoiceDetailsRepo.findBySalesInvoiceId(id);
    }

    @Transactional
    public SalesInvoiceDetail saveItemInOrder(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SalesInvoiceDetail salesInvoiceDetail = new SalesInvoiceDetail();
        //map from DTO to the Original Entity

        return mapSalesInvoiceDetailsDtoToSalesInvoiceDetails(parsedInvoiceItemDto, salesInvoiceDetail);

    }
    @Transactional
    public SalesInvoiceDetail updateItemInOrder(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SalesInvoiceDetail supplierOrderDetails = salesInvoiceDetailsRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get();
        //map from DTO to the Original Entity
        return mapSalesInvoiceDetailsDtoToSalesInvoiceDetails(parsedInvoiceItemDto, supplierOrderDetails);
        //
    }
    @Transactional
    public SalesInvoiceDetail updateItemBeingInsertedAgain(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SalesInvoiceDetail salesInvoiceDetail = salesInvoiceDetailsRepo.findBySalesInvoiceIdAndItemIdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(),parsedInvoiceItemDto.getUom()).orElseThrow();
        salesInvoiceDetail.setQuantity(salesInvoiceDetail.getQuantity().add(parsedInvoiceItemDto.getDeliveredQuantity()));
        salesInvoiceDetail.setTotalPrice(salesInvoiceDetail.getUnitPrice().multiply(salesInvoiceDetail.getQuantity()));
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself

        float totalPrice=0;
        for(SalesInvoiceDetail details : salesInvoiceDetailsRepo.findBySalesInvoiceId(parsedInvoiceItemDto.getOrderId())){
            System.out.println(details.getTotalPrice());
            totalPrice+=details.getTotalPrice().floatValue();
        }

        System.out.println("totalPrice : "+totalPrice);
        SalesInvoice salesInvoice = salesInvoiceRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        salesInvoice.setTotalCost(BigDecimal.valueOf(totalPrice));
        System.out.println("totalPrice in obj : "+salesInvoice.getTotalCost());
        salesInvoice.setTotalBeforeDiscount(salesInvoice.getTotalCost().add(salesInvoice.getTaxValue()==null? BigDecimal.ZERO:salesInvoice.getTaxValue()));
        System.out.println("totalPrice : "+salesInvoice.getTotalBeforeDiscount());
        System.out.println("totalPrice : "+salesInvoice.getTaxValue());
        salesInvoiceRepo.save(salesInvoice);
        totalPrice=0;
        return salesInvoiceDetailsRepo.save(salesInvoiceDetail);
    }

    @Transactional
    public SalesInvoice deleteItemFromSalesInvoice(Long id) {
        SalesInvoiceDetail salesInvoiceDetail = salesInvoiceDetailsRepo.findById(id).orElseThrow();
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself
        salesInvoiceDetailsRepo.deleteById(id);
        float totalPrice=0;
        for(SalesInvoiceDetail details : salesInvoiceDetailsRepo.findBySalesInvoiceId(salesInvoiceDetail.getSalesInvoice().getId())){
            totalPrice+=details.getTotalPrice().floatValue();
        }
        SalesInvoice salesInvoice = salesInvoiceRepo.findById(salesInvoiceDetail.getSalesInvoice().getId()).orElseThrow();
        salesInvoice.setTotalCost(BigDecimal.valueOf(totalPrice));
        salesInvoice.setTotalBeforeDiscount(salesInvoice.getTotalCost().add(salesInvoice.getTaxValue()==null? BigDecimal.ZERO:salesInvoice.getTaxValue()));
        salesInvoice.setUpdatedBy(new Admin(1));
        totalPrice=0;
        return salesInvoiceRepo.save(salesInvoice);
    }
    @Transactional
    public SalesInvoiceDetail mapSalesInvoiceDetailsDtoToSalesInvoiceDetails(InvoiceItemDTO parsedInvoiceItemDto, SalesInvoiceDetail salesInvoiceDetail) {


        salesInvoiceDetail.setSalesInvoice(new SalesInvoice(parsedInvoiceItemDto.getOrderId()));
        salesInvoiceDetail.setItem(new InvItemCard(parsedInvoiceItemDto.getInvItemCard()));
        salesInvoiceDetail.setUom(new InvUom(parsedInvoiceItemDto.getUom()));
        salesInvoiceDetail.setQuantity(parsedInvoiceItemDto.getDeliveredQuantity());
        salesInvoiceDetail.setUnitPrice(parsedInvoiceItemDto.getUnitPrice());
        salesInvoiceDetail.setTotalPrice(parsedInvoiceItemDto.getUnitPrice().multiply(parsedInvoiceItemDto.getDeliveredQuantity()==null?BigDecimal.ONE:parsedInvoiceItemDto.getDeliveredQuantity()));
        salesInvoiceDetail.setIsParentUom(invUomRepo.findById(parsedInvoiceItemDto.getUom()).get().isMaster());
        salesInvoiceDetail.setItem(new InvItemCard(parsedInvoiceItemDto.getInvItemCard()));
        salesInvoiceDetail.setSalesItemType((byte) invItemCardRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get().getItemType());
        //saving the salesInvoiceDetail in the DB.

        SalesInvoiceDetail savedSalesInvoiceDetails= salesInvoiceDetailsRepo.save(salesInvoiceDetail);

        System.out.println("saved savedSalesInvoiceDetails service method");
        //updating the Order itself with the updates.
        SalesInvoice salesInvoice = salesInvoiceRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        salesInvoice.setTotalCost(salesInvoiceRepo.findById(parsedInvoiceItemDto.getOrderId()).get().getTotalCost().add(salesInvoiceDetail.getTotalPrice()==null?BigDecimal.ZERO:salesInvoiceDetail.getTotalPrice()));
        salesInvoice.setTotalBeforeDiscount(salesInvoice.getTotalCost().add(salesInvoice.getTaxValue()==null? BigDecimal.ZERO:salesInvoice.getTaxValue()));
        salesInvoiceRepo.save(salesInvoice);

        return savedSalesInvoiceDetails;
    }
    @Transactional
    public boolean checkOrderDetailsItemIsApproved(Long id){
        SalesInvoiceDetail salesInvoiceDetail = salesInvoiceDetailsRepo.findById(id).orElseThrow();
        SalesInvoice salesInvoice= salesInvoiceRepo.findById(id).orElseThrow();
        return salesInvoice.getIsApproved();
    }
    public boolean checkItemInOrderOrNot(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {
        System.out.println("entered checkItemInORderOrNot method");
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getOrderId());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getUom());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getInvItemCard());
        Optional<SalesInvoiceDetail> salesInvoiceDetails= salesInvoiceDetailsRepo.findBySalesInvoiceIdAndItemIdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(), parsedInvoiceItemDto.getUom());
        if (salesInvoiceDetails.isEmpty()){
            return false;
        }else{
            System.out.println(salesInvoiceDetails.get().getSalesInvoice().getId());
            System.out.println(salesInvoiceDetails.get().getItem().getId());
            System.out.println(salesInvoiceDetails.get().getUom().getId());
            return true;
        }
    }
}

