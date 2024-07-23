package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.ApproveSupplierOrderDTO;
import com.erp.greenlight.DTOs.SupplierOrderDTO;
import com.erp.greenlight.models.SupplierOrder;
import com.erp.greenlight.services.StoreService;
import com.erp.greenlight.services.SupplierOrderService;
import com.erp.greenlight.services.SupplierService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/supplierOrder")
 
public class SupplierOrderController {

    @Autowired
    private SupplierOrderService supplierOrderService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SupplierService supplierService;
    @GetMapping("")
    public ResponseEntity<Object>  getAllSupplier_Store_SupplierWithOrders(
            @RequestParam int pageIndex,
            @RequestParam int pageSize
    ){
        Map<String, Object> data = new HashMap<>();
        data.put("suppliers",supplierService.getAllSuppliers());
        data.put("suppliersWithOrders",supplierOrderService.getAllSupplierOrders(pageIndex, pageSize));
        data.put("stores",storeService.findAll());

        return AppResponse.generateResponse("all_data", HttpStatus.OK, data , true);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getSupplierOrderById(@PathVariable Long id){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,Optional.of(supplierOrderService.getSupplierOrderById(id).get()) , true);

    }

    @PostMapping()
    public ResponseEntity<Object> saveSupplierOrder(@RequestBody SupplierOrderDTO supplierOrderDTO){
        return AppResponse.generateResponse("تم حفط فاتورة المشتريات بنجاح", HttpStatus.OK,supplierOrderService.saveSupplierOrder(supplierOrderDTO) , true);
    }
    @PutMapping()
    public ResponseEntity<Object> updateSupplierOrder(@RequestBody  SupplierOrderDTO supplierOrderDTO){
        return AppResponse.generateResponse("تم تحديث اوردر المورد بنجاح", HttpStatus.OK,supplierOrderService.updateSupplierOrder(supplierOrderDTO) , true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSupplierOrder(@PathVariable Long id){
        if(supplierOrderService.checkOrderIsApproved(id)){
            return AppResponse.generateResponse("تعذر حذف الفاتورة لأنها مغلقه", HttpStatus.OK,null , true);
        }
        return AppResponse.generateResponse("تم حذف الفاتورة بمحتوياتها بنجاح", HttpStatus.OK,  supplierOrderService.deleteSupplierOrder(id) , true);
    }

    @PostMapping("/approve")
    public ResponseEntity<Object> approve(@RequestBody ApproveSupplierOrderDTO request){
        return supplierOrderService.approve(request);
    }

    @GetMapping("/search")
    public ResponseEntity<Object>  search(
            @RequestParam(name = "id" ,required = false) Long id,
            @RequestParam(name = "supplierId" ,required = false) Long supplierId,
            @RequestParam(name = "storeId" ,required = false) Integer storeId,
            @RequestParam(name = "fromDate" ,required = false) LocalDate fromDate,
            @RequestParam(name = "toDate" ,required = false) LocalDate toDate
    ){


        return AppResponse.generateResponse("all_data", HttpStatus.OK, supplierOrderService.search(
                id, storeId,  supplierId, fromDate, toDate
        ) , true);
    }


}
