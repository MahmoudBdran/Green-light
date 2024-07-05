package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.DTOs.SaveSarfPermissionDTO;
import com.erp.greenlight.DTOs.SaveSarfPermissionDetailsDTO;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.*;
import com.erp.greenlight.utils.AppResponse;
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
public class SarfPermissionService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    StoreRepo storeRepo;

    @Autowired
    SarfPermissionRepo sarfPermissionRepo;

    @Autowired
    InvItemCardRepo invItemCardRepo;

    @Autowired
    InvUomRepo invUomRepo;

    @Autowired
    SarfPermissionDetailsRepo sarfPermissionDetailsRepo;


    public SarfPermission save(SaveSarfPermissionDTO request) {

        SarfPermission dataToInsert = new SarfPermission();


        dataToInsert.setCustomer(customerRepo.findById(request.getCustomer()).orElseThrow());
        dataToInsert.setTotalCost(BigDecimal.ZERO);
        dataToInsert.setNotes(request.getNotes());
        dataToInsert.setReceiverName(request.getReceiverName());
        dataToInsert.setPermissionDate(request.getPermissionDate());

        return sarfPermissionRepo.save(dataToInsert);
    }

    public SarfPermission update(SaveSarfPermissionDTO request) {
        SarfPermission dataToUpdate = sarfPermissionRepo.findById(request.getId()).orElseThrow();

        dataToUpdate.setCustomer(customerRepo.findById(request.getCustomer()).orElseThrow());
        dataToUpdate.setTotalCost(BigDecimal.ZERO);
        dataToUpdate.setNotes(request.getNotes());
        dataToUpdate.setReceiverName(request.getReceiverName());
        dataToUpdate.setPermissionDate(request.getPermissionDate());

        return sarfPermissionRepo.save(dataToUpdate);
    }

    @Transactional
    public boolean delete(Long id) {
        for (SarfPermissionDetail details : sarfPermissionRepo.findById(id).get().getDetailsItems()) {
            sarfPermissionDetailsRepo.deleteById(details.getId());
        }
        sarfPermissionRepo.deleteById(id);
        return true;

    }


    @Transactional
    public SarfPermission saveItem(SaveSarfPermissionDetailsDTO request) {

        SarfPermissionDetail dataToInsert = new SarfPermissionDetail();
        SarfPermission permission = sarfPermissionRepo.findById(request.getPermissionId()).orElseThrow();

        dataToInsert.setStore(storeRepo.findById(request.getStore()).orElseThrow());
        dataToInsert.setItem(invItemCardRepo.findById(request.getInvItemCard()).orElseThrow());
        dataToInsert.setUom(invUomRepo.findById(request.getUom()).orElseThrow());
        dataToInsert.setSarfPermission(permission);
        dataToInsert.setQuantity(request.getItemQuantity());

        sarfPermissionDetailsRepo.save(dataToInsert);

        return permission;
    }

    @Transactional
    public SarfPermission deleteItem(Long id) {

        Long permissionId =  sarfPermissionDetailsRepo.findById(id).orElseThrow().getSarfPermission().getId();
        SarfPermission permission = sarfPermissionRepo.findById(permissionId).orElseThrow();
        sarfPermissionDetailsRepo.deleteById(id);

        return sarfPermissionRepo.findById(permissionId).orElseThrow();
    }




}

