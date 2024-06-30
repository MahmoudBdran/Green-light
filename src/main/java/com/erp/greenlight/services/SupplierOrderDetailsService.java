package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.InvoiceItemDTO;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.InvItemCardRepo;
import com.erp.greenlight.repositories.InvUomRepo;
import com.erp.greenlight.repositories.SupplierOrderDetailsRepo;
import com.erp.greenlight.repositories.SupplierOrderRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierOrderDetailsService {
    @Autowired
    SupplierOrderDetailsRepo supplierOrderDetailsRepo;
    @Autowired
    SupplierOrderRepo supplierOrderRepo;
    @Autowired
    InvUomRepo invUomRepo;
    @Autowired
    InvItemCardRepo invItemCardRepo;
    public List<SupplierOrderDetails> findByOrderId(Long id){

        return supplierOrderDetailsRepo.findByOrderId(id);
    }

    @Transactional
    public SupplierOrder saveItemInOrder(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SupplierOrderDetails supplierOrderDetails = new SupplierOrderDetails();
        //map from DTO to the Original Entity

        return mapSupplierOrderDetailsDtoToSupplierOrderDetails(parsedInvoiceItemDto, supplierOrderDetails);

    }
    @Transactional
    public SupplierOrder updateItemInOrder(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SupplierOrderDetails supplierOrderDetails = supplierOrderDetailsRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get();
        //map from DTO to the Original Entity
        return mapSupplierOrderDetailsDtoToSupplierOrderDetails(parsedInvoiceItemDto, supplierOrderDetails);
        //
    }
    @Transactional
    public SupplierOrderDetails updateItemBeingInsertedAgain(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SupplierOrderDetails supplierOrderDetails = supplierOrderDetailsRepo.findByOrderIdAndInvItemCard_IdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(),parsedInvoiceItemDto.getUom()).orElseThrow();
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
        supplierOrder.setTotalBeforeDiscount(supplierOrder.getTotalCost().add(supplierOrder.getTaxValue()==null? BigDecimal.ZERO:supplierOrder.getTaxValue()));
        System.out.println("totalPrice : "+supplierOrder.getTotalBeforeDiscount());
        System.out.println("totalPrice : "+supplierOrder.getTaxValue());
        supplierOrderRepo.save(supplierOrder);
        totalPrice=0;
        return supplierOrderDetailsRepo.save(supplierOrderDetails);
    }

    @Transactional
    public  SupplierOrder deleteItemFromSupplierOrder(Long id) {
        SupplierOrderDetails supplierOrderDetails = supplierOrderDetailsRepo.findById(id).orElseThrow();
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself
        supplierOrderDetailsRepo.deleteById(id);
        float totalPrice=0;
        for(SupplierOrderDetails details : supplierOrderDetailsRepo.findByOrderId(supplierOrderDetails.getOrder().getId())){
            totalPrice+=details.getTotalPrice().floatValue();
        }
        SupplierOrder supplierOrder =supplierOrderRepo.findById(supplierOrderDetails.getOrder().getId()).orElseThrow();
        supplierOrder.setTotalCost(BigDecimal.valueOf(totalPrice));
        supplierOrder.setTotalBeforeDiscount(supplierOrder.getTotalCost().add(supplierOrder.getTaxValue()==null? BigDecimal.ZERO:supplierOrder.getTaxValue()));
        totalPrice=0;
         supplierOrderRepo.save(supplierOrder);
        return supplierOrder;
    }
    @Transactional
    public SupplierOrder mapSupplierOrderDetailsDtoToSupplierOrderDetails(InvoiceItemDTO parsedInvoiceItemDto,SupplierOrderDetails supplierOrderDetails) {


        InvItemCard invItemCard = invItemCardRepo.findById(parsedInvoiceItemDto.getInvItemCard()).orElseThrow();
        InvUom invUom = invUomRepo.findById(parsedInvoiceItemDto.getUom()).orElseThrow();

        supplierOrderDetails.setOrder(new SupplierOrder(parsedInvoiceItemDto.getOrderId()));
        supplierOrderDetails.setInvItemCard(invItemCard);
        supplierOrderDetails.setUom(invUom);
        supplierOrderDetails.setDeliveredQuantity(parsedInvoiceItemDto.getDeliveredQuantity());
        supplierOrderDetails.setUnitPrice(parsedInvoiceItemDto.getUnitPrice());
        supplierOrderDetails.setTotalPrice(parsedInvoiceItemDto.getUnitPrice().multiply(parsedInvoiceItemDto.getDeliveredQuantity()==null?BigDecimal.ONE:parsedInvoiceItemDto.getDeliveredQuantity()));
        supplierOrderDetails.setOrderType(supplierOrderRepo.findById(parsedInvoiceItemDto.getOrderId()).get().getOrderType());
        supplierOrderDetails.setIsParentUom(invUomRepo.findById(parsedInvoiceItemDto.getUom()).get().isMaster());
        supplierOrderDetails.setBatchAutoSerial(1L);
        supplierOrderDetails.setItemCardType((byte) invItemCardRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get().getItemType());
        //saving the supplierOrderDetails in the DB.

        SupplierOrderDetails savedSupplierOrderDetails= supplierOrderDetailsRepo.save(supplierOrderDetails);
        System.out.println("saved savedSupplierOrderDetails service method");
        //updating the Order itself with the updates.
        SupplierOrder supplierOrder =supplierOrderRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        supplierOrder.setTotalCost(supplierOrderRepo.findById(parsedInvoiceItemDto.getOrderId()).get().getTotalCost().add(supplierOrderDetails.getTotalPrice()==null?BigDecimal.ZERO:supplierOrderDetails.getTotalPrice()));
        supplierOrder.setTotalBeforeDiscount(supplierOrder.getTotalCost().add(supplierOrder.getTaxValue()==null? BigDecimal.ZERO:supplierOrder.getTaxValue()));
        supplierOrderRepo.save(supplierOrder);


      return supplierOrderRepo.findById(supplierOrderDetails.getOrder().getId()).orElseThrow();
    }
    @Transactional
    public boolean checkOrderDetailsItemIsApproved(Long id){
        SupplierOrderDetails supplierOrderDetails = supplierOrderDetailsRepo.findById(id).orElseThrow();
        SupplierOrder supplierOrder=supplierOrderRepo.findById(supplierOrderDetails.getOrder().getId()).orElseThrow();
        return supplierOrder.getIsApproved();
    }
    public boolean checkItemInOrderOrNot(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {
        System.out.println("entered checkItemInORderOrNot method");
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getOrderId());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getUom());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getInvItemCard());
        Optional<SupplierOrderDetails> supplierOrderDetails=supplierOrderDetailsRepo.findByOrderIdAndInvItemCard_IdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(), parsedInvoiceItemDto.getUom());
        if (supplierOrderDetails.isEmpty()){
            return false;
        }else{
            System.out.println(supplierOrderDetails.get().getOrder().getId());
            System.out.println(supplierOrderDetails.get().getInvItemCard().getId());
            System.out.println(supplierOrderDetails.get().getUom().getId());
            return true;
        }
    }
}

