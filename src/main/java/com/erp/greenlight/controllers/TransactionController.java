package com.erp.greenlight.controllers;


import com.erp.greenlight.DTOs.SaveTreasuryTransactionDto;
import com.erp.greenlight.repositories.InvItemCardBatchRepo;
import com.erp.greenlight.services.AccountService;
import com.erp.greenlight.services.InvItemCardService;
import com.erp.greenlight.services.TreasuresTransactionService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:4200")

public class TransactionController {

    @Autowired
    InvItemCardBatchRepo invItemCardBatchRepo;

    @Autowired
    AccountService accountService;


    @Autowired
    InvItemCardService invItemCardService;

    @Autowired
    TreasuresTransactionService treasuresTransactionService;

    @GetMapping("/collectFindAll")
    public ResponseEntity<Object> collectFindAll( @RequestParam int pageIndex,
                                                  @RequestParam int pageSize) {

        Map<String, Object> data = new HashMap<>();
        data.put("accounts", accountService.getAllAccounts());
        data.put("treasuresTransactions", treasuresTransactionService.collectFindAll(pageIndex, pageSize));
        data.put("availableBalance", treasuresTransactionService.getAvailableBalance());

        return AppResponse.generateResponse("all_data", HttpStatus.OK, data, true);
    }

    @GetMapping("/exchangeFindAll")
    public ResponseEntity<Object> exchangeFindAll( @RequestParam int pageIndex,
                                                   @RequestParam int pageSize) {

        Map<String, Object> data = new HashMap<>();
        data.put("accounts", accountService.getAllAccounts());
        data.put("treasuresTransactions", treasuresTransactionService.exChangeFindAll(pageIndex, pageSize));
        data.put("availableBalance", treasuresTransactionService.getAvailableBalance());

        return AppResponse.generateResponse("all_data", HttpStatus.OK, data, true);
    }

    @PostMapping("/collect")
    public ResponseEntity<Object> collect(@RequestBody SaveTreasuryTransactionDto request) {

        Map<String, Object> data = new HashMap<>();
        data.put("accounts", accountService.getAllAccounts());
        data.put("transaction",treasuresTransactionService.collect(request));
        data.put("availableBalance", treasuresTransactionService.getAvailableBalance());

        return AppResponse.generateResponse("تم التحصيل بنجاح", HttpStatus.OK, data, true);
    }


    @PostMapping("/exchange")
    public ResponseEntity<Object> exchange(@RequestBody SaveTreasuryTransactionDto request) {

        Map<String, Object> data = new HashMap<>();
        data.put("accounts", accountService.getAllAccounts());
        data.put("transaction",treasuresTransactionService.exchange(request));
        data.put("availableBalance", treasuresTransactionService.getAvailableBalance());

        return AppResponse.generateResponse("تم الصرف بنجاح", HttpStatus.OK, data, true);
    }




}
