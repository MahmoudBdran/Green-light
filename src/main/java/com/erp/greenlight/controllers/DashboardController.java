package com.erp.greenlight.controllers;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.repositories.AdminPanelSettingsRepo;
import com.erp.greenlight.repositories.CustomerRepo;
import com.erp.greenlight.repositories.SalesInvoiceRepo;
import com.erp.greenlight.repositories.SupplierRepo;
import com.erp.greenlight.services.AccountService;
import com.erp.greenlight.services.AccountTypeService;
import com.erp.greenlight.services.SalesInvoiceService;
import com.erp.greenlight.services.TreasuresTransactionService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost")

public class DashboardController {

    @Autowired
    TreasuresTransactionService treasuresTransactionService;

    @Autowired
    SalesInvoiceRepo salesInvoiceRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    SupplierRepo supplierRepo;

    @Autowired
    SalesInvoiceService salesInvoiceService;


    @GetMapping
    public ResponseEntity<Object> getData(){

        Map<String, Object> data = new HashMap<>();

        data.put("availableBalance",  treasuresTransactionService.getAvailableBalance());
        data.put("last5Transaction",  treasuresTransactionService.getLast5Transaction());
        data.put("last5Sales",  salesInvoiceRepo.getLast5SalesApproved());
        data.put("customerCount", customerRepo.countAllRows());
        data.put("supplierCount", supplierRepo.countAllRows());
        data.put("chartData", salesInvoiceService.getSalesInvoicesByMonth());


        return AppResponse.generateResponse("all_data", HttpStatus.OK, data , true);
    }


 


}
