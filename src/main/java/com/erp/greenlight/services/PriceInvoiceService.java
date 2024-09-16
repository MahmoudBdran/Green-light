package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.DTOs.SalesInvoiceItemDTO;
import com.erp.greenlight.DTOs.SavePriceInvoiceDTO;
import com.erp.greenlight.DTOs.SavePriceInvoiceDetailsDTO;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.*;
import com.erp.greenlight.utils.AppResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PriceInvoiceService {


    @Autowired
    PriceInvoiceRepo priceInvoiceRepo;

    @Autowired
    PriceInvoiceDetailsRepo priceInvoiceDetailsRepo;

    @Autowired
    InvItemCardRepo invItemCardRepo;

    @Autowired
    InvUomRepo invUomRepo;


    public List<PriceInvoice> findAll() {
        return priceInvoiceRepo.findAll();
    }

    public Optional<PriceInvoice> findById(Long id) {
        return Optional.of(priceInvoiceRepo.findById(id).get());
    }

    public PriceInvoice save(SavePriceInvoiceDTO request) {

        PriceInvoice dataToInsert = new PriceInvoice();

        dataToInsert.setNote(request.getNote());
        dataToInsert.setInvoiceDate(request.getInvoiceDate());
        dataToInsert.setIsApproved(Boolean.FALSE);
        dataToInsert.setTotalCost(BigDecimal.ZERO);
        dataToInsert.setCustomer(request.getCustomer());
        dataToInsert.setDeliveryLocation(request.getDeliveryLocation());
        dataToInsert.setOfferDuration(request.getOfferDuration());
        dataToInsert.setTaxIncluded(request.isTaxIncluded());

        return priceInvoiceRepo.save(dataToInsert);
    }

    public PriceInvoice update(SavePriceInvoiceDTO request) {
        PriceInvoice dataToUpdate = priceInvoiceRepo.findById(request.getId()).orElseThrow();

        dataToUpdate.setInvoiceDate(request.getInvoiceDate());
        dataToUpdate.setIsApproved(Boolean.FALSE);
        dataToUpdate.setNote(request.getNote());


        dataToUpdate.setCustomer(request.getCustomer());
        dataToUpdate.setDeliveryLocation(request.getDeliveryLocation());
        dataToUpdate.setOfferDuration(request.getOfferDuration());
        dataToUpdate.setTaxIncluded(request.isTaxIncluded());

        return priceInvoiceRepo.save(dataToUpdate);
    }

    @Transactional
    public boolean delete(Long id) {
      /*  for (SalesInvoiceDetail salesInvoiceDetail : salesInvoiceRepo.findById(id).get().getSalesInvoiceDetails()) {
            salesInvoiceDetailsService.deleteItem(salesInvoiceDetail.getId());
        }
        salesInvoiceRepo.deleteById(id);
        return true;*/

        return false;

    }

    @Transactional
    public boolean checkOrderIsApproved(Long id) {
        PriceInvoice priceInvoice = priceInvoiceRepo.findById(id).orElseThrow();
        return priceInvoice.getIsApproved();
    }


    @Transactional
    public PriceInvoice saveItem(SavePriceInvoiceDetailsDTO request) throws JsonProcessingException {

        PriceInvoice priceInvoice = priceInvoiceRepo.findById(request.getOrderId()).orElseThrow();

        InvItemCard invItemCard = invItemCardRepo.findById(request.getInvItemCard()).orElseThrow();
        InvUom invUom = invUomRepo.findById(request.getUom()).orElseThrow();

        PriceInvoiceDetail dataToInsertToInvoiceDetails = new PriceInvoiceDetail();

        dataToInsertToInvoiceDetails.setItem(invItemCard);
        dataToInsertToInvoiceDetails.setUom(invUom);
        dataToInsertToInvoiceDetails.setQuantity(request.getItemQuantity());
        dataToInsertToInvoiceDetails.setUnitPrice(request.getUnitPrice());
        dataToInsertToInvoiceDetails.setTotalPrice(request.getUnitPrice().multiply(request.getItemQuantity()));
        dataToInsertToInvoiceDetails.setPriceInvoice(priceInvoice);
        priceInvoice.setTotalCost(priceInvoice.getTotalCost().add(dataToInsertToInvoiceDetails.getTotalPrice()));

        priceInvoiceRepo.save(priceInvoice);
        priceInvoiceDetailsRepo.save(dataToInsertToInvoiceDetails);

        return priceInvoice;

    }


    @Transactional
    public PriceInvoice deleteItemFromPriceInvoice(Long id) {
        PriceInvoiceDetail priceInvoiceDetail = priceInvoiceDetailsRepo.findById(id).orElseThrow();
        deleteItem(id);
        return priceInvoiceRepo.findById(priceInvoiceDetail.getPriceInvoice().getId()).orElseThrow();
    }


    @Transactional
    public void deleteItem(Long id) {
        PriceInvoiceDetail priceInvoiceDetail = priceInvoiceDetailsRepo.findById(id).orElseThrow();
        PriceInvoice priceInvoice = priceInvoiceRepo.findById(priceInvoiceDetail.getPriceInvoice().getId()).orElseThrow();
        InvItemCard invItemCard = invItemCardRepo.findById(priceInvoiceDetail.getItem().getId()).orElseThrow();
        InvUom invUom = invUomRepo.findById(priceInvoiceDetail.getUom().getId()).orElseThrow();

        priceInvoiceDetailsRepo.deleteById(id);

        List<PriceInvoiceDetail> newDetails = priceInvoiceDetailsRepo.findByPriceInvoiceId(priceInvoice.getId());
        float totalPrice = 0;
        for (PriceInvoiceDetail details : newDetails) {
            totalPrice += details.getTotalPrice().floatValue();
        }
        priceInvoice.setTotalCost(BigDecimal.valueOf(totalPrice));
        priceInvoiceRepo.save(priceInvoice);
    }

    public boolean isItemInInvoice(SavePriceInvoiceDetailsDTO request){

        Optional<PriceInvoiceDetail> priceInvoiceDetail = priceInvoiceDetailsRepo.findByPriceInvoiceIdAndItemIdAndUomId(request.getOrderId(), request.getInvItemCard(), request.getUom());

        if (priceInvoiceDetail.isEmpty()) {
            return false;
        } else {

            return true;
        }

    }





}

