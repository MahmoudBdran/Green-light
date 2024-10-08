package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.DTOs.SaveSarfPermissionDTO;
import com.erp.greenlight.DTOs.SaveSarfPermissionDetailsDTO;
import com.erp.greenlight.models.SalesInvoice;
import com.erp.greenlight.repositories.InvItemCardBatchRepo;
import com.erp.greenlight.repositories.InvUomRepo;
import com.erp.greenlight.repositories.SarfPermissionDetailsRepo;
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
@CrossOrigin(origins = "http://localhost")
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
    SarfPermissionDetailsRepo sarfPermissionDetailsRepo;

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

        if(sarfPermissionService.isApproved(request.getId())){
            return AppResponse.generateResponse("عفوا غير قادر علي التعدبل لان الاذن معتمد", HttpStatus.OK, null , true);
        }else{
            return AppResponse.generateResponse("تم تحديث اذن الصرف  بنجاح", HttpStatus.OK, sarfPermissionService.update(request) , true);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        if(sarfPermissionService.isApproved(id)){
            return AppResponse.generateResponse("عفوا غير قادر علي المسح لان الاذن معتمد", HttpStatus.OK, null , true);
        }else {
            return AppResponse.generateResponse("تم حذف اذن الصرف  بمحتوياتها بنجاح", HttpStatus.OK, sarfPermissionService.delete(id), true);
        }
    }

    @PostMapping("/approve")
    public ResponseEntity<Object> approve(@RequestBody SaveSarfPermissionDTO request){
       return sarfPermissionService.approve(request);
    }

    /* -------      details -------------------*/
    @PostMapping("/saveItem")
    public ResponseEntity<Object> saveItem(@RequestBody SaveSarfPermissionDetailsDTO request){
        if(sarfPermissionService.isApproved(request.getPermissionId())){
            return AppResponse.generateResponse("عفوا غير قادر علي الاضافة لان الاذن معتمد", HttpStatus.OK, null , true);
        }else {
            return AppResponse.generateResponse("تم حفط اذن الصرف بنجاح", HttpStatus.OK, sarfPermissionService.saveItem(request), true);
        }
    }


    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable Long id){
        if(sarfPermissionService.isApproved(sarfPermissionDetailsRepo.findById(id).orElseThrow().getSarfPermission().getId())){
            return AppResponse.generateResponse("عفوا غير قادر علي المسح لان الاذن معتمد", HttpStatus.OK, null , true);
        }else {
            return AppResponse.generateResponse("تم حذف العنصر ", HttpStatus.OK, sarfPermissionService.deleteItem(id), true);
        }
    }

}
