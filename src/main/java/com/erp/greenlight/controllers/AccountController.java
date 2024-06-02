package com.erp.greenlight.controllers;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.InvItemcard;
import com.erp.greenlight.services.AccountService;
import com.erp.greenlight.services.AccountTypeService;
import com.erp.greenlight.services.InvItemCardService;
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
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://localhost:4200")

public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountTypeService accountTypeService;

    @GetMapping
    public ResponseEntity<Object> findAll(){

        Map<String, Object> data = new HashMap<>();

        data.put("accounts",  accountService.getAllAccounts());
        data.put("accountTypes",  accountTypeService.getAllAccountTypes());
        data.put("parentAccounts",  accountService.getParentAccounts());

        return AppResponse.generateResponse("all_data", HttpStatus.OK, data , true);
    }
    @GetMapping("/{id}")
    public Optional<Account> getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody Account account){
       return AppResponse.generateResponse("تم حفط الحساب بنجاح", HttpStatus.OK, accountService.saveAccount(account) , true);
    }

    @PutMapping("/update")
    public Account updateAccount(@RequestBody Account account){
        return accountService.saveAccount(account);
    }



}
