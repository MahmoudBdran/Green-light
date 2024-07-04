package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.DTOs.SaveSarfPermissionDTO;
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
    SarfPermissionDetailsRepo sarfPermissionDetailsRepo;


    public SarfPermission save(SaveSarfPermissionDTO request) {

        SarfPermission dataToInsert = new SarfPermission();


        dataToInsert.setCustomer(customerRepo.findById(request.getCustomer()).orElseThrow());
        dataToInsert.setTotalCost(BigDecimal.ZERO);
        dataToInsert.setStore(storeRepo.findById(request.getStore()).orElseThrow());
        dataToInsert.setNotes(request.getNotes());

        return sarfPermissionRepo.save(dataToInsert);
    }

    public SarfPermission update(SaveSarfPermissionDTO request) {
        SarfPermission dataToUpdate = sarfPermissionRepo.findById(request.getId()).orElseThrow();

        dataToUpdate.setCustomer(customerRepo.findById(request.getCustomer()).orElseThrow());
        dataToUpdate.setTotalCost(BigDecimal.ZERO);
        dataToUpdate.setStore(storeRepo.findById(request.getStore()).orElseThrow());
        dataToUpdate.setNotes(request.getNotes());

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






}

