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
public class SalesInvoiceReturnDetailsService {
    @Autowired
    SalesInvoiceReturnDetailsRepo salesInvoiceReturnDetailsRepo;

    @Autowired
    InvUomRepo invUomRepo;
    @Autowired
    InvItemCardRepo invItemCardRepo;
    @Autowired
    private SalesInvoiceReturnRepo salesInvoiceReturnRepo;

    public List<SalesInvoicesReturnDetails> findBySalesInvoiceReturnId(Long id){

        return salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnId(id);
    }

    @Transactional
    public SalesInvoicesReturnDetails saveItemInOrder(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SalesInvoicesReturnDetails salesInvoicesReturnDetails = new SalesInvoicesReturnDetails();
        //map from DTO to the Original Entity

        return mapSalesInvoiceReturnDetailsDtoToSalesInvoiceReturnDetails(parsedInvoiceItemDto, salesInvoicesReturnDetails);

    }
    @Transactional
    public SalesInvoicesReturnDetails updateItemInOrder(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SalesInvoicesReturnDetails salesInvoicesReturnDetails = salesInvoiceReturnDetailsRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get();
        //map from DTO to the Original Entity
        return mapSalesInvoiceReturnDetailsDtoToSalesInvoiceReturnDetails(parsedInvoiceItemDto, salesInvoicesReturnDetails);
        //
    }
    @Transactional
    public SalesInvoicesReturnDetails updateItemBeingInsertedAgain(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SalesInvoicesReturnDetails salesInvoiceDetail = salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnIdAndItemIdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(),parsedInvoiceItemDto.getUom()).orElseThrow();
        salesInvoiceDetail.setQuantity(salesInvoiceDetail.getQuantity().add(parsedInvoiceItemDto.getDeliveredQuantity()));
        salesInvoiceDetail.setTotalPrice(salesInvoiceDetail.getUnitPrice().multiply(salesInvoiceDetail.getQuantity()));
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself

        float totalPrice=0;
        for(SalesInvoicesReturnDetails details : salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnId(parsedInvoiceItemDto.getOrderId())){
            System.out.println(details.getTotalPrice());
            totalPrice+=details.getTotalPrice().floatValue();
        }

        System.out.println("totalPrice : "+totalPrice);
        SalesInvoiceReturn salesInvoiceReturn = salesInvoiceReturnRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        salesInvoiceReturn.setTotalCost(BigDecimal.valueOf(totalPrice));
        System.out.println("totalPrice in obj : "+salesInvoiceReturn.getTotalCost());
        salesInvoiceReturn.setTotalBeforeDiscount(salesInvoiceReturn.getTotalCost().add(salesInvoiceReturn.getTaxValue()==null? BigDecimal.ZERO:salesInvoiceReturn.getTaxValue()));
        System.out.println("totalPrice : "+salesInvoiceReturn.getTotalBeforeDiscount());
        System.out.println("totalPrice : "+salesInvoiceReturn.getTaxValue());
        salesInvoiceReturnRepo.save(salesInvoiceReturn);
        totalPrice=0;
        return salesInvoiceReturnDetailsRepo.save(salesInvoiceDetail);
    }

    @Transactional
    public SalesInvoiceReturn deleteItemFromSalesInvoiceReturn(Long id) {
        SalesInvoicesReturnDetails salesInvoiceDetail = salesInvoiceReturnDetailsRepo.findById(id).orElseThrow();
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself
        salesInvoiceReturnDetailsRepo.deleteById(id);
        float totalPrice=0;
        for(SalesInvoicesReturnDetails details : salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnId(salesInvoiceDetail.getSalesInvoiceReturn().getId())){
            totalPrice+=details.getTotalPrice().floatValue();
        }
        SalesInvoiceReturn salesInvoiceReturn = salesInvoiceReturnRepo.findById(salesInvoiceDetail.getSalesInvoiceReturn().getId()).orElseThrow();
        salesInvoiceReturn.setTotalCost(BigDecimal.valueOf(totalPrice));
        salesInvoiceReturn.setTotalBeforeDiscount(salesInvoiceReturn.getTotalCost().add(salesInvoiceReturn.getTaxValue()==null? BigDecimal.ZERO:salesInvoiceReturn.getTaxValue()));
        salesInvoiceReturn.setUpdatedBy(new Admin(1));
        totalPrice=0;
        return salesInvoiceReturnRepo.save(salesInvoiceReturn);
    }
    @Transactional
    public SalesInvoicesReturnDetails mapSalesInvoiceReturnDetailsDtoToSalesInvoiceReturnDetails(InvoiceItemDTO parsedInvoiceItemDto, SalesInvoicesReturnDetails salesInvoicesReturnDetails) {


        salesInvoicesReturnDetails.setSalesInvoiceReturn(new SalesInvoiceReturn(parsedInvoiceItemDto.getOrderId()));
        salesInvoicesReturnDetails.setItem(new InvItemCard(parsedInvoiceItemDto.getInvItemCard()));
        salesInvoicesReturnDetails.setUom(new InvUom(parsedInvoiceItemDto.getUom()));
        salesInvoicesReturnDetails.setQuantity(parsedInvoiceItemDto.getDeliveredQuantity());
        salesInvoicesReturnDetails.setUnitPrice(parsedInvoiceItemDto.getUnitPrice());
        salesInvoicesReturnDetails.setTotalPrice(parsedInvoiceItemDto.getUnitPrice().multiply(parsedInvoiceItemDto.getDeliveredQuantity()==null?BigDecimal.ONE:parsedInvoiceItemDto.getDeliveredQuantity()));
        salesInvoicesReturnDetails.setIsparentuom(invUomRepo.findById(parsedInvoiceItemDto.getUom()).get().isMaster());
        salesInvoicesReturnDetails.setItem(new InvItemCard(parsedInvoiceItemDto.getInvItemCard()));
        salesInvoicesReturnDetails.setSalesItemType((byte) invItemCardRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get().getItemType());
        //saving the salesInvoicesReturnDetails in the DB.

        SalesInvoicesReturnDetails savedSalesInvoiceDetails= salesInvoiceReturnDetailsRepo.save(salesInvoicesReturnDetails);

        System.out.println("saved savedSalesInvoiceDetails service method");
        //updating the Order itself with the updates.
        SalesInvoiceReturn salesInvoiceReturn = salesInvoiceReturnRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        salesInvoiceReturn.setTotalCost(salesInvoiceReturnRepo.findById(parsedInvoiceItemDto.getOrderId()).get().getTotalCost().add(salesInvoicesReturnDetails.getTotalPrice()==null?BigDecimal.ZERO:salesInvoicesReturnDetails.getTotalPrice()));
        salesInvoiceReturn.setTotalBeforeDiscount(salesInvoiceReturn.getTotalCost().add(salesInvoiceReturn.getTaxValue()==null? BigDecimal.ZERO:salesInvoiceReturn.getTaxValue()));
        salesInvoiceReturnRepo.save(salesInvoiceReturn);

        return savedSalesInvoiceDetails;
    }
    @Transactional
    public boolean checkOrderDetailsItemIsApproved(Long id){
        SalesInvoicesReturnDetails salesInvoicesReturnDetails = salesInvoiceReturnDetailsRepo.findById(id).orElseThrow();
        SalesInvoiceReturn salesInvoiceReturn= salesInvoiceReturnRepo.findById(id).orElseThrow();
        return salesInvoiceReturn.getIsApproved();
    }
    public boolean checkItemInOrderOrNot(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {
        System.out.println("entered checkItemInORderOrNot method");
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getOrderId());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getUom());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getInvItemCard());
        Optional<SalesInvoicesReturnDetails> salesInvoiceReturnDetails= salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnIdAndItemIdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(), parsedInvoiceItemDto.getUom());
        if (salesInvoiceReturnDetails.isEmpty()){
            return false;
        }else{
            System.out.println(salesInvoiceReturnDetails.get().getSalesInvoiceReturn().getId());
            System.out.println(salesInvoiceReturnDetails.get().getItem().getId());
            System.out.println(salesInvoiceReturnDetails.get().getUom().getId());
            return true;
        }
    }
}

