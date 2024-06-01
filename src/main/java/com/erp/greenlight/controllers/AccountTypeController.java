package com.erp.greenlight.controllers;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.AccountType;
import com.erp.greenlight.services.AccountService;
import com.erp.greenlight.services.AccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountTypeController {

    @Autowired
    AccountTypeService service;

    @GetMapping
    public List<AccountType> getAllAccountTypes(){
        return service.getAllAccountTypes();
    }
    @GetMapping("/{id}")
    public Optional<AccountType> getAccountTypeById(@PathVariable Long id){
        return service.getAccountTypeById(id);
    }

    @PostMapping("/save")
    public AccountType saveAccountType(@RequestBody AccountType accountType){
        return service.saveAccountType(accountType);
    }

    @PutMapping("/update")
    public AccountType updateAccountType(@RequestBody AccountType accountType){
        return service.saveAccountType(accountType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccountType(@PathVariable Long id){
        if(service.getAccountTypeById(id).isPresent()){
            service.deleteAccountType(id);
            return new ResponseEntity<>("deleted Successfully", HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("We cannot find account type Id : "+id,HttpStatus.BAD_REQUEST);

        }
    }


}
