package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.SaveInventoryDTO;
import com.erp.greenlight.DTOs.SaveSarfPermissionDTO;
import com.erp.greenlight.DTOs.SaveSarfPermissionDetailsDTO;
import com.erp.greenlight.repositories.InventoryRepo;
import com.erp.greenlight.repositories.SarfPermissionRepo;
import com.erp.greenlight.repositories.StoreRepo;
import com.erp.greenlight.services.*;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:4200")
public class InventoryController {

    @Autowired
    private SarfPermissionService sarfPermissionService;
    @Autowired
    private StoreService storeService;


    @Autowired
    CustomerService customerService;

    @Autowired
    StoreRepo storeRepo;


    @Autowired
    InventoryRepo inventoryRepo;

    @Autowired
    InventoryService inventoryService;

    @GetMapping("")
    public ResponseEntity<Object>  findAll(){
        Map<String, Object> data = new HashMap<>();
        data.put("stores", storeRepo.findAll());
        data.put("inventories",inventoryRepo.findAll());

        return AppResponse.generateResponse("all_data", HttpStatus.OK, data , true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,Optional.of(inventoryRepo.findById(id).orElseThrow()) , true);
    }

    @PostMapping()
    public ResponseEntity<Object> save(@RequestBody SaveInventoryDTO request){
        return AppResponse.generateResponse("تم حفط اذن الصرف بنجاح", HttpStatus.OK, inventoryService.save(request) , true);
    }
    @PutMapping()
    public ResponseEntity<Object> update(@RequestBody SaveInventoryDTO request){
        return AppResponse.generateResponse("تم تحديث اذن الصرف  بنجاح", HttpStatus.OK, inventoryService.update(request) , true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return AppResponse.generateResponse("تم حذف اذن الصرف  بمحتوياتها بنجاح", HttpStatus.OK,  sarfPermissionService.delete(id) , true);
    }

    /* -------      details -------------------*/
    @PostMapping("/saveItem")
    public ResponseEntity<Object> saveItem(@RequestBody SaveSarfPermissionDetailsDTO request){
        return AppResponse.generateResponse("تم حفط اذن الصرف بنجاح", HttpStatus.OK, sarfPermissionService.saveItem(request) , true);
    }


    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable Long id){
        return AppResponse.generateResponse("تم حذف العنصر ", HttpStatus.OK,  sarfPermissionService.deleteItem(id) , true);
    }

}
