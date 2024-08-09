package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.GetItemBatchDto;
import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.models.InvItemCard;
import com.erp.greenlight.models.InvItemCardBatch;
import com.erp.greenlight.models.SalesInvoice;
import com.erp.greenlight.models.SupplierOrder;
import com.erp.greenlight.repositories.InvItemCardBatchRepo;
import com.erp.greenlight.repositories.InvUomRepo;
import com.erp.greenlight.services.CustomerService;
import com.erp.greenlight.services.InvItemCardService;
import com.erp.greenlight.services.SalesInvoiceService;
import com.erp.greenlight.services.StoreService;
import com.erp.greenlight.utils.AppResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
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
@CrossOrigin(origins = {"http://localhost", "https://animated-sprinkles-d16b69.netlify.app"})

@RequestMapping("/salesInvoice")
 
public class SalesInvoiceController {

    @Autowired
    private SalesInvoiceService salesInvoiceService;
    @Autowired
    private StoreService storeService;

    @Autowired
    InvItemCardService invItemCardService;

    @Autowired
    CustomerService customerService;

    @Autowired
    InvItemCardBatchRepo invItemCardBatchRepo;

    @Autowired
    InvUomRepo invUomRepo;

    @GetMapping("")
    public ResponseEntity<Object>  getAllSupplier_Store_SupplierWithOrders(
            @RequestParam int pageIndex,
            @RequestParam int pageSize){
        Map<String, Object> data = new HashMap<>();
        data.put("sales", salesInvoiceService.getAllSalesInvoices(pageIndex, pageSize));
        data.put("customers",customerService.getAllCustomers());

        return AppResponse.generateResponse("all_data", HttpStatus.OK, data , true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSalesInvoiceById(@PathVariable Long id){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,Optional.of(salesInvoiceService.getSalesInvoiceById(id).get()) , true);

    }

    @PostMapping()
    public ResponseEntity<Object> saveSalesInvoice(@RequestBody SalesInvoiceDTO salesInvoiceDTO){
        return AppResponse.generateResponse("تم حفط فاتورة المبيعات بنجاح", HttpStatus.OK, salesInvoiceService.saveSalesInvoice(salesInvoiceDTO) , true);
    }
    @PutMapping()
    public ResponseEntity<Object> updateSalesInvoice(@RequestBody SalesInvoiceDTO salesInvoiceDTO){
        return AppResponse.generateResponse("تم تحديث اوردر العميل بنجاح", HttpStatus.OK, salesInvoiceService.updateSalesInvoice(salesInvoiceDTO) , true);
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
       return salesInvoiceService.approveSalesInvoice(salesInvoice);
    }




    @GetMapping("/getAllData")
    public ResponseEntity<Object>  getAllData(){
        Map<String, Object> data = new HashMap<>();
        data.put("stores", storeService.findAll());
        data.put("invItems", invItemCardService.getAllInvItemCards());

        return AppResponse.generateResponse("all_data", HttpStatus.OK, data , true);
    }



    @GetMapping("/search")
    public ResponseEntity<Object>  search(
            @RequestParam(name = "id" ,required = false) Long id,
            @RequestParam(name = "customerId" ,required = false) Long customerId,
            @RequestParam(name = "fromDate" ,required = false) LocalDate fromDate,
            @RequestParam(name = "toDate" ,required = false) LocalDate toDate
    ){


        return AppResponse.generateResponse("all_data", HttpStatus.OK, salesInvoiceService.search(
                id, customerId, fromDate, toDate
        ) , true);
    }


}
