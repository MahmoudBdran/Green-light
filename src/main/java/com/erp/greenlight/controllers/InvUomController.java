package com.erp.greenlight.controllers;

import com.erp.greenlight.models.Customer;
import com.erp.greenlight.models.InvUom;
import com.erp.greenlight.services.CustomerService;
import com.erp.greenlight.services.InvUomService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/InvUoms")
@CrossOrigin(origins = "http://localhost")

public class InvUomController {

    @Autowired
    InvUomService service;

    @GetMapping
    public ResponseEntity<Object> getAllInvUom(){

        return AppResponse.generateResponse("all_data", HttpStatus.OK, service.getAllInvUoms() , true);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getInvUomById(@PathVariable Long id){

        return AppResponse.generateResponse("all_data", HttpStatus.OK, service.getInvUomById(id) , true);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveInvUom(@RequestBody InvUom invUom){
        return AppResponse.generateResponse("تم الحفظ", HttpStatus.OK,  service.saveInvUom(invUom) , true);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateInvUom(@RequestBody InvUom invUom){
        return AppResponse.generateResponse("تم التحديث", HttpStatus.OK,   service.saveInvUom(invUom) , true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInvUom(@PathVariable Long id){
        if(service.getInvUomById(id).isPresent()){
            service.deleteInvUom(id);
            return AppResponse.generateResponse("تم حذف الوحده بنجاح", HttpStatus.OK,null, true);
        }else{
            return AppResponse.generateResponse("الوحده غير موجوده بالفعل", HttpStatus.BAD_REQUEST,null,false);

        }
    }


}
