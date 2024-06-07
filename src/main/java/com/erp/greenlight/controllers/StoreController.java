package com.erp.greenlight.controllers;


import com.erp.greenlight.models.Store;
import com.erp.greenlight.services.StoreService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores")
@CrossOrigin(origins = "http://localhost:4200")

public class StoreController {

    @Autowired
    StoreService storeService;

    @GetMapping
    public ResponseEntity<Object> findAll(){

        return AppResponse.generateResponse("all_data", HttpStatus.OK, storeService.findAll() , true);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Long id){

        return AppResponse.generateResponse("all_data", HttpStatus.OK, storeService.findById(id) , true);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody Store store){
       return AppResponse.generateResponse("تم حفط المخزن بنجاح", HttpStatus.OK, storeService.save(store) , true);
    }


    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Store store){
        return AppResponse.generateResponse("تم حفط المخزن بنجاح", HttpStatus.OK, storeService.save(store) , true);
    }


}
