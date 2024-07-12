package com.erp.greenlight.controllers;

import com.erp.greenlight.models.SupplierCategory;
import com.erp.greenlight.services.SupplierCategoryService;
import com.erp.greenlight.services.SupplierService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplierCategory")
@CrossOrigin(origins = "http://localhost:4200")
public class SupplierCategoryController {
    @Autowired
    private SupplierCategoryService supplierCategoryService;


    @GetMapping()
    public ResponseEntity<Object> getAllSupplierCategory() {
        return AppResponse.generateResponse("all_data", HttpStatus.OK,supplierCategoryService.findAll() , true);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSupplierCategoryById(@PathVariable Integer id) {
        return AppResponse.generateResponse("all_data", HttpStatus.OK,supplierCategoryService.findById(id) , true);

    }
    @PostMapping()
    public ResponseEntity<Object> createSupplierCategory(@RequestBody SupplierCategory supplierCategory) {
        return AppResponse.generateResponse("تم حفط نوع المورد بنجاح", HttpStatus.OK,supplierCategoryService.save(supplierCategory) , true);

    }
    @PutMapping()
    public ResponseEntity<Object> updateSupplierCategory(@RequestBody SupplierCategory supplierCategory) {
        return AppResponse.generateResponse("تم تحديث نوع المورد بنجاح", HttpStatus.OK,supplierCategoryService.save(supplierCategory) , true);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSupplierCategory(@RequestBody Integer id) {
        return AppResponse.generateResponse("تم حذف نوع المورد بنجاح", HttpStatus.OK, supplierCategoryService.deleteById(id) , true);


    }

}
