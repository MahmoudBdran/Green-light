package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.models.SalesInvoice;
import com.erp.greenlight.models.SalesInvoiceReturn;
import com.erp.greenlight.services.CustomerService;
import com.erp.greenlight.services.SalesInvoiceReturnService;
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
@RequestMapping("/salesInvoiceReturn")
@CrossOrigin(origins = "http://localhost:4200")
public class SalesInvoiceReturnController {

    @Autowired
    private SalesInvoiceReturnService salesInvoiceReturnService;
    @Autowired
    private StoreService storeService;

    @Autowired
    CustomerService customerService;

    @GetMapping("")
    public ResponseEntity<Object>  getAllCustomers_Store_InvoiceServiceReturn(){
        Map<String, Object> data = new HashMap<>();
        data.put("salesReturn", salesInvoiceReturnService.getAllSalesInvoicesReturn());
        data.put("customers",customerService.getAllCustomers());

        return AppResponse.generateResponse("all_data", HttpStatus.OK, data , true);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getSalesInvoiceReturnById(@PathVariable Long id){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,Optional.of(salesInvoiceReturnService.getSalesInvoiceReturnById(id).get()) , true);
    }

    @PostMapping()
    public ResponseEntity<Object> saveSalesInvoiceReturn(@RequestBody SalesInvoiceDTO salesInvoiceDTO){
        return AppResponse.generateResponse("تم حفط فاتورة مرتجع المبيعات بنجاح", HttpStatus.OK, salesInvoiceReturnService.saveSalesInvoiceReturn(salesInvoiceDTO) , true);
    }
    @PutMapping()
    public ResponseEntity<Object> updateSalesInvoiceReturn(@RequestBody SalesInvoiceDTO salesInvoiceDTO){
        return AppResponse.generateResponse("تم تحديث اوردر مرتجع مبيعات بنجاح", HttpStatus.OK, salesInvoiceReturnService.updateSalesInvoiceReturn(salesInvoiceDTO) , true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSalesInvoiceReturn(@PathVariable Long id){
        if(salesInvoiceReturnService.checkOrderIsApproved(id)){
            return AppResponse.generateResponse("تعذر حذف الفاتورة لأنها مغلقه", HttpStatus.BAD_REQUEST,null , false);
        }
        return AppResponse.generateResponse("تم حذف الفاتورة بمحتوياتها بنجاح", HttpStatus.OK,  salesInvoiceReturnService.deleteSalesInvoiceReturn(id) , true);
    }


    @PostMapping("/approveSalesReturnInvoice")
    public ResponseEntity<Object> approveSalesInvoiceReturn(@RequestBody SalesInvoiceReturn salesInvoiceReturn){
        if(salesInvoiceReturnService.checkOrderIsApproved(salesInvoiceReturn.getId())){
            return AppResponse.generateResponse("الفاتوره بالفعل محفوظه", HttpStatus.BAD_REQUEST,null , false);
        }else{

            return AppResponse.generateResponse("تم حفظ الفاتورة بنجاح", HttpStatus.OK,  salesInvoiceReturnService.approveSalesInvoiceReturn(salesInvoiceReturn) , true);
        }
    }


}
