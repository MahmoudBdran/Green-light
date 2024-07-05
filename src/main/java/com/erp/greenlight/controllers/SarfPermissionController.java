package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.DTOs.SaveSarfPermissionDTO;
import com.erp.greenlight.DTOs.SaveSarfPermissionDetailsDTO;
import com.erp.greenlight.models.SalesInvoice;
import com.erp.greenlight.repositories.InvItemCardBatchRepo;
import com.erp.greenlight.repositories.InvUomRepo;
import com.erp.greenlight.repositories.SarfPermissionRepo;
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
@RequestMapping("/sarfPermissions")
@CrossOrigin(origins = "http://localhost:4200")
public class SarfPermissionController {

    @Autowired
    private SarfPermissionService sarfPermissionService;
    @Autowired
    private StoreService storeService;

    @Autowired
    InvItemCardService invItemCardService;

    @Autowired
    SarfPermissionRepo sarfPermissionRepo;

    @Autowired
    CustomerService customerService;

    @GetMapping("")
    public ResponseEntity<Object>  findAll(){
        Map<String, Object> data = new HashMap<>();
        data.put("customers", customerService.getAllCustomers());
        data.put("sarfPermissions",sarfPermissionRepo.findAll());

        return AppResponse.generateResponse("all_data", HttpStatus.OK, data , true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,Optional.of(sarfPermissionRepo.findById(id).orElseThrow()) , true);
    }

    @PostMapping()
    public ResponseEntity<Object> save(@RequestBody SaveSarfPermissionDTO request){
        return AppResponse.generateResponse("تم حفط اذن الصرف بنجاح", HttpStatus.OK, sarfPermissionService.save(request) , true);
    }
    @PutMapping()
    public ResponseEntity<Object> update(@RequestBody SaveSarfPermissionDTO request){
        return AppResponse.generateResponse("تم تحديث اذن الصرف  بنجاح", HttpStatus.OK, sarfPermissionService.update(request) , true);
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
