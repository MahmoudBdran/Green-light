package com.erp.greenlight.controllers;


import com.erp.greenlight.repositories.InvItemCardBatchRepo;
import com.erp.greenlight.services.AccountService;
import com.erp.greenlight.services.InvItemCardService;
import com.erp.greenlight.services.TreasuresTransactionService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/collect")
    public ResponseEntity<Object> collectFindAll() {

        Map<String, Object> data = new HashMap<>();
        data.put("accounts", accountService.getAllAccounts());
        data.put("treasuresTransactions", treasuresTransactionService.collectFindAll());
        data.put("availableBalance", 0);

        return AppResponse.generateResponse("all_batches", HttpStatus.OK, data, true);
    }

    @GetMapping("/exchange")
    public ResponseEntity<Object> exchangeFindAll() {

        Map<String, Object> data = new HashMap<>();
        data.put("accounts", accountService.getAllAccounts());
        data.put("treasuresTransactions", treasuresTransactionService.exChangeFindAll());
        data.put("availableBalance", 0);

        return AppResponse.generateResponse("all_batches", HttpStatus.OK, data, true);
    }



}
