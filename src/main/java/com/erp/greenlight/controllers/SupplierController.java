package com.erp.greenlight.controllers;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.Customer;
import com.erp.greenlight.models.Supplier;
import com.erp.greenlight.repositories.SupplierRepo;
import com.erp.greenlight.services.AccountService;
import com.erp.greenlight.services.SupplierService;
import com.erp.greenlight.utils.AppResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/suppliers")
@CrossOrigin(origins = "http://localhost:4200")
public class SupplierController {
    @Autowired
    SupplierService supplierService;
    @Autowired
    AccountService accountService;

    @GetMapping
    public ResponseEntity<Object> getAllSuppliers(){

        Map<String, Object> data = new HashMap<>();

        data.put("suppliers",  supplierService.getAllSuppliers());
        data.put("supplierCategories",  supplierService.getAllSupplierCategories());


        return AppResponse.generateResponse("all_data", HttpStatus.OK, data , true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSupplierById(@PathVariable Long id){
        return AppResponse.generateResponse("all_data", supplierService.getSupplierById(id) .isEmpty()?HttpStatus.BAD_REQUEST:HttpStatus.OK, supplierService.getSupplierById(id) , supplierService.getSupplierById(id) .isEmpty()?false:true);

    }


    @PostMapping("/create")
    public ResponseEntity<Object> saveSupplier(@RequestBody Supplier supplier){
        Supplier savedSupplier = supplierService.saveSupplier(supplier);
        if(savedSupplier==null){
            return AppResponse.generateResponse("لم يتم حفظ المورد والحساب لوجود الحساب بالفعل", HttpStatus.BAD_REQUEST, savedSupplier, false);

        }else{
            return AppResponse.generateResponse("تم حفط المورد وتم عمل حساب له بنجاح", HttpStatus.OK, savedSupplier, true);
        }


    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<Object> updateSupplier(@RequestBody Supplier supplier){

        return AppResponse.generateResponse("تم تحديث معلومات المورد بنجاح", HttpStatus.OK, supplierService.updateSupplier(supplier) , true);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteSupplier(@PathVariable Long id){
        if(supplierService.getSupplierById(id).isPresent()){
            supplierService.deleteSupplier(id);
            return AppResponse.generateResponse("تم حذف المورد والحساب الخاص به بنجاح", HttpStatus.OK,"deleted" , true);
        }else{
            return AppResponse.generateResponse("لم نتمكن من ايجاد المورد", HttpStatus.BAD_REQUEST, "ERROR", true);

        }
    }

}
