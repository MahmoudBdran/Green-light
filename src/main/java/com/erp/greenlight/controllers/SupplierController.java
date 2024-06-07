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
        return AppResponse.generateResponse("all_data", HttpStatus.OK, supplierService.getAllSuppliers() , true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSupplierById(@PathVariable Long id){
        return AppResponse.generateResponse("all_data", supplierService.getSupplierById(id) .isEmpty()?HttpStatus.BAD_REQUEST:HttpStatus.OK, supplierService.getSupplierById(id) , supplierService.getSupplierById(id) .isEmpty()?false:true);

    }


    @PostMapping("/create")
    public ResponseEntity<Object> saveSupplier(@RequestBody Supplier supplier){
        Supplier savedSupplier = supplierService.saveSupplier(supplier);
        if(savedSupplier==null){
            return AppResponse.generateResponse("لم يتم حفظ الحساب لوجود الحساب بالفعل", HttpStatus.BAD_REQUEST, savedSupplier, false);

        }else{
            return AppResponse.generateResponse("تم حفط الحساب بنجاح", HttpStatus.OK, savedSupplier, true);
        }


    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<Object> updateSupplier(@RequestBody Supplier supplier){

        Long supplierId = supplier.getId();
        Supplier existingSupplier = supplierService.getSupplierById(supplierId).orElseThrow();

        existingSupplier.setName(supplier.getName() != null ? supplier.getName() : existingSupplier.getName());
        existingSupplier.setAddress(supplier.getAddress() != null ? supplier.getAddress() : existingSupplier.getAddress());
        existingSupplier.setPhones(supplier.getPhones() != null ? supplier.getPhones() : existingSupplier.getPhones());
        existingSupplier.setNotes(supplier.getNotes() != null ? supplier.getNotes() : existingSupplier.getNotes());

        // Update timestamp regardless// Maintain existing active state if not provided
            existingSupplier.setIsActive(supplier.getIsActive());


        Supplier savedSupplier =supplierService.saveSupplier(existingSupplier);

        Account existingAccount = savedSupplier.getAccountNumber();
        existingAccount.setName(supplier.getName());
        accountService.saveAccount(existingAccount);
        return AppResponse.generateResponse("تم تحديث الحساب بنجاح", HttpStatus.OK, savedSupplier, true);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteSupplier(@PathVariable Long id){
        if(supplierService.getSupplierById(id).isPresent()){
            supplierService.deleteSupplier(id);
            return AppResponse.generateResponse("تم حذف الحساب بنجاح", HttpStatus.OK,"deleted" , true);
        }else{
            return AppResponse.generateResponse("لم نتمكن من ايجاد الحساب", HttpStatus.BAD_REQUEST, "ERROR", true);

        }
    }

}
