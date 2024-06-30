package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.ApproveSupplierOrderDTO;
import com.erp.greenlight.DTOs.SupplierOrderDTO;
import com.erp.greenlight.models.SupplierOrder;
import com.erp.greenlight.services.StoreService;
import com.erp.greenlight.services.SupplierOrderReturnService;
import com.erp.greenlight.services.SupplierService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/supplierOrderReturn")
@CrossOrigin(origins = "http://localhost:4200")
public class SupplierOrderReturnController {

    @Autowired
    private SupplierOrderReturnService supplierOrderReturnService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SupplierService supplierService;
    @GetMapping("")
    public ResponseEntity<Object>  getAllSupplier_Store_SupplierWithOrdersReturns(){
        Map<String, Object> data = new HashMap<>();
        data.put("suppliers",supplierService.getAllSuppliers());
        data.put("suppliersWithOrdersReturns", supplierOrderReturnService.getAllSupplierOrdersReturns());
        data.put("stores",storeService.findAll());

        return AppResponse.generateResponse("all_data", HttpStatus.OK, data , true);
    }


/*    @GetMapping()
    public ResponseEntity<Object> getAllSupplierOrders(){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,supplierOrderService.getAllSupplierOrders() , true);

    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSupplierOrderReturnById(@PathVariable Long id){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,Optional.of(supplierOrderReturnService.getSupplierOrderReturnById(id).get()) , true);

    }

    @PostMapping()
    public ResponseEntity<Object> saveSupplierOrderReturn(@RequestBody SupplierOrderDTO supplierOrderDTO){
        return AppResponse.generateResponse("تم حفط فاتورة مرتجع المشتريات بنجاح", HttpStatus.OK, supplierOrderReturnService.saveSupplierOrderReturn(supplierOrderDTO) , true);
    }
    @PutMapping()
    public ResponseEntity<Object> updateSupplierOrderReturn(@RequestBody SupplierOrder supplierOrderReturn){
        return AppResponse.generateResponse("تم تحديث اوردر مرتجعات المورد بنجاح", HttpStatus.OK, supplierOrderReturnService.updateSupplierOrderReturn(supplierOrderReturn) , true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSupplierOrderReturn(@PathVariable Long id){
        if(supplierOrderReturnService.checkOrderReturnIsApproved(id)){
            return AppResponse.generateResponse("تعذر حذف الفاتورة لأنها مغلقه", HttpStatus.BAD_REQUEST,null , false);
        }
        return AppResponse.generateResponse("تم حذف الفاتورة بمحتوياتها بنجاح", HttpStatus.OK,  supplierOrderReturnService.deleteSupplierOrderReturn(id) , true);
    }

    @PostMapping("/approve")
    public ResponseEntity<Object> approve(@RequestBody ApproveSupplierOrderDTO request){
        return supplierOrderReturnService.approve(request);

    }


}
