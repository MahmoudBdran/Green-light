package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.models.SalesInvoice;
import com.erp.greenlight.models.SupplierOrder;
import com.erp.greenlight.services.CustomerService;
import com.erp.greenlight.services.SalesInvoiceService;
import com.erp.greenlight.services.StoreService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/salesInvoice")
@CrossOrigin(origins = "http://localhost:4200")
public class SalesInvoiceController {

    @Autowired
    private SalesInvoiceService salesInvoiceService;
    @Autowired
    private StoreService storeService;

    @Autowired
    CustomerService customerService;

    @GetMapping("")
    public ResponseEntity<Object>  getAllSupplier_Store_SupplierWithOrders(){
        Map<String, Object> data = new HashMap<>();
        data.put("sales", salesInvoiceService.getAllSalesInvoices());
        data.put("customers",customerService.getAllCustomers());

        return AppResponse.generateResponse("all_data", HttpStatus.OK, data , true);
    }


/*    @GetMapping()
    public ResponseEntity<Object> getAllSupplierOrders(){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,supplierOrderService.getAllSupplierOrders() , true);

    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSalesInvoiceById(@PathVariable Long id){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,Optional.of(salesInvoiceService.getSalesInvoiceById(id).get()) , true);

    }

    @PostMapping()
    public ResponseEntity<Object> saveSalesInvoice(@RequestBody SalesInvoiceDTO salesInvoiceDTO){
        return AppResponse.generateResponse("تم حفط فاتورة المبيعات بنجاح", HttpStatus.OK, salesInvoiceService.saveSalesInvoice(salesInvoiceDTO) , true);
    }
    @PutMapping()
    public ResponseEntity<Object> updateSalesInvoice(SalesInvoice salesInvoice){
        return AppResponse.generateResponse("تم تحديث اوردر المورد بنجاح", HttpStatus.OK, salesInvoiceService.updateSalesInvoice(salesInvoice) , true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSalesInvoice(@PathVariable Long id){
        if(salesInvoiceService.checkOrderIsApproved(id)){
            return AppResponse.generateResponse("تعذر حذف الفاتورة لأنها مغلقه", HttpStatus.BAD_REQUEST,null , false);
        }
        return AppResponse.generateResponse("تم حذف الفاتورة بمحتوياتها بنجاح", HttpStatus.OK,  salesInvoiceService.deleteSalesInvoice(id) , true);
    }


    @PostMapping("/approveSalesInvoice")
    public ResponseEntity<Object> approveSalesInvoice(@RequestBody SalesInvoice salesInvoice){
        if(salesInvoiceService.checkOrderIsApproved(salesInvoice.getId())){
            return AppResponse.generateResponse("الفاتوره بالفعل محفوظه", HttpStatus.BAD_REQUEST,null , false);
        }else{

            return AppResponse.generateResponse("تم حفظ الفاتورة بنجاح", HttpStatus.OK,  salesInvoiceService.approveSalesInvoice(salesInvoice) , true);
        }
    }


}
