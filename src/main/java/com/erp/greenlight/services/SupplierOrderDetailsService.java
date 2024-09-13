package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.InvoiceItemDTO;
import com.erp.greenlight.DTOs.SalesInvoiceItemDTO;
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
    public SupplierOrder saveItemInOrder(InvoiceItemDTO request) throws JsonProcessingException {

        InvItemCard invItemCard = invItemCardRepo.findById(request.getInvItemCard()).orElseThrow();
        InvUom invUom = invUomRepo.findById(request.getUom()).orElseThrow();

        SupplierOrderDetails supplierOrderDetails = new SupplierOrderDetails();

        BigDecimal rowTotalCoat = request.getUnitPrice().multiply(request.getDeliveredQuantity());


        supplierOrderDetails.setOrder(new SupplierOrder(request.getOrderId()));
        supplierOrderDetails.setInvItemCard(invItemCard);
        supplierOrderDetails.setUom(invUom);
        supplierOrderDetails.setDeliveredQuantity(request.getDeliveredQuantity());
        supplierOrderDetails.setUnitPrice(request.getUnitPrice());
        supplierOrderDetails.setTotalPrice(rowTotalCoat);
        supplierOrderDetails.setOrderType(supplierOrderRepo.findById(request.getOrderId()).get().getOrderType());
        supplierOrderDetails.setIsParentUom(invUomRepo.findById(request.getUom()).get().isMaster());
        supplierOrderDetails.setItemCardType((byte) invItemCardRepo.findById(request.getInvItemCard()).get().getItemType());

        supplierOrderDetailsRepo.save(supplierOrderDetails);

        SupplierOrder supplierOrder =supplierOrderRepo.findById(request.getOrderId()).orElseThrow();

        supplierOrder.setTotalCost(supplierOrder.getTotalCost().add(rowTotalCoat));
        supplierOrder.setTotalBeforeDiscount(supplierOrder.getTotalCost());
        supplierOrderRepo.save(supplierOrder);

        return supplierOrder;
    }
    @Transactional
    public SupplierOrder updateItemInOrder(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SupplierOrderDetails supplierOrderDetails = supplierOrderDetailsRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get();
        //map from DTO to the Original Entity
       // return mapSupplierOrderDetailsDtoToSupplierOrderDetails(parsedInvoiceItemDto, supplierOrderDetails);
        return null;
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
    public boolean checkOrderDetailsItemIsApproved(Long id){
        SupplierOrderDetails supplierOrderDetails = supplierOrderDetailsRepo.findById(id).orElseThrow();
        SupplierOrder supplierOrder=supplierOrderRepo.findById(supplierOrderDetails.getOrder().getId()).orElseThrow();
        return supplierOrder.getIsApproved();
    }
    public boolean checkItemInOrderOrNot(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        Optional<SupplierOrderDetails> supplierOrderDetails=supplierOrderDetailsRepo.findByOrderIdAndInvItemCardIdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(), parsedInvoiceItemDto.getUom());
        if (supplierOrderDetails.isEmpty()){
            return false;
        }else{
            return true;
        }
    }


    @Transactional
    public SupplierOrder overWriteItemInOrder(InvoiceItemDTO invoiceItemDTO) throws JsonProcessingException {
        Optional<SupplierOrderDetails> supplierOrderDetails = supplierOrderDetailsRepo.findByOrderIdAndInvItemCardIdAndUomId(invoiceItemDTO.getOrderId(), invoiceItemDTO.getInvItemCard(), invoiceItemDTO.getUom());


        if(supplierOrderDetails.isPresent()){
            Long oldItemDetailsId = supplierOrderDetails.get().getId();
            BigDecimal oldItemQuantity = supplierOrderDetails.get().getDeliveredQuantity();
            deleteItemFromSupplierOrder(oldItemDetailsId);
            invoiceItemDTO.setDeliveredQuantity(oldItemQuantity.add(invoiceItemDTO.getDeliveredQuantity()));

            return saveItemInOrder(invoiceItemDTO);
        }
        return null;
    }
}

