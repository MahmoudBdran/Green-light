package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.SaveInventoryDTO;
import com.erp.greenlight.DTOs.SaveInventoryDetailsDTO;
import com.erp.greenlight.models.InvItemCardBatch;
import com.erp.greenlight.models.InvStoresInventory;
import com.erp.greenlight.models.SarfPermission;
import com.erp.greenlight.models.SarfPermissionDetail;
import com.erp.greenlight.repositories.*;
import com.erp.greenlight.utils.AppResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {

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

    @Autowired
    InventoryRepo inventoryRepo;

    @Autowired
    InvItemCardBatchRepo invItemCardBatchRepo;

    public InvStoresInventory save(SaveInventoryDTO request) {

        InvStoresInventory dataToInsert = new InvStoresInventory();

        dataToInsert.setNotes(request.getNotes());
        dataToInsert.setDate(request.getDate());
        dataToInsert.setStore(storeRepo.findById(request.getStore()).orElseThrow());
        dataToInsert.setInventoryType(request.getInventoryType());

        return inventoryRepo.save(dataToInsert);
    }

    public InvStoresInventory update(SaveInventoryDTO request) {
        InvStoresInventory dataToUpdate =inventoryRepo.findById(request.getId()).orElseThrow();

        dataToUpdate.setNotes(request.getNotes());
        dataToUpdate.setDate(request.getDate());
        dataToUpdate.setStore(storeRepo.findById(request.getStore()).orElseThrow());
        dataToUpdate.setInventoryType(request.getInventoryType());

        return inventoryRepo.save(dataToUpdate);
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
    public ResponseEntity<Object> saveItem(SaveInventoryDetailsDTO request) {

        InvStoresInventory inventory = inventoryRepo.findById(request.getInventoryId()).orElseThrow();

        if(inventory.getIsClosed()){
            return AppResponse.generateResponse("عفوا غير قادر علي الاضافة لان الجرد معتمد", HttpStatus.OK, null , true);
        }

        List<InvItemCardBatch> batches = new ArrayList<>();

        if(request.getDoesAddAllItems()){
            batches = invItemCardBatchRepo.findAllByStoreId(request.getStore());
        }else{
            batches = invItemCardBatchRepo.findAllByStoreIdAndItemId(request.getStore(), null);
        }
         return  null;
    }

    @Transactional
    public InvStoresInventory deleteItem(Long id) {

        Long permissionId =  sarfPermissionDetailsRepo.findById(id).orElseThrow().getSarfPermission().getId();
        SarfPermission permission = sarfPermissionRepo.findById(permissionId).orElseThrow();
        sarfPermissionDetailsRepo.deleteById(id);

        return null;
    }




}

