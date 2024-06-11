package com.erp.greenlight.controllers;

import com.erp.greenlight.models.SupplierOrder;
import com.erp.greenlight.services.StoreService;
import com.erp.greenlight.services.SupplierOrderService;
import com.erp.greenlight.services.SupplierService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/supplierOrder")
@CrossOrigin(origins = "http://localhost:4200")
public class SupplierOrderController {

    @Autowired
    private SupplierOrderService supplierOrderService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SupplierService supplierService;
    @GetMapping("")
    public ResponseEntity<Object>  getAllSupplier_Store_SupplierWithOrders(){
        Map<String, Object> data = new HashMap<>();
        data.put("suppliers",supplierService.getAllSuppliers());
        data.put("suppliersWithOrders",supplierOrderService.getAllSupplierOrders());
        data.put("stores",storeService.findAll());

        return AppResponse.generateResponse("all_data", HttpStatus.OK, data , true);
    }


/*    @GetMapping()
    public ResponseEntity<Object> getAllSupplierOrders(){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,supplierOrderService.getAllSupplierOrders() , true);

    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSupplierOrderById(@PathVariable Long id){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,Optional.of(supplierOrderService.getSupplierOrderById(id).get()) , true);

    }

    @PostMapping()
    public ResponseEntity<Object> saveSupplierOrder(SupplierOrder supplierOrder){
        return AppResponse.generateResponse("تم حفط اوردر المورد بنجاح", HttpStatus.OK,supplierOrderService.saveSupplierOrder(supplierOrder) , true);
    }
    @PutMapping()
    public ResponseEntity<Object> updateSupplierOrder(SupplierOrder supplierOrder){
        return AppResponse.generateResponse("تم تحديث اوردر المورد بنجاح", HttpStatus.OK,supplierOrderService.saveSupplierOrder(supplierOrder) , true);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplierOrder(@PathVariable Long id){
        supplierOrderService.deleteSupplierOrder(id);
    }


}
