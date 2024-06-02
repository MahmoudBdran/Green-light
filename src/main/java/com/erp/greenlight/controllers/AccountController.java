package com.erp.greenlight.controllers;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.InvItemcard;
import com.erp.greenlight.services.AccountService;
import com.erp.greenlight.services.InvItemCardService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://localhost:4200")

public class AccountController {

    @Autowired
    AccountService service;

    @GetMapping
    public ResponseEntity<Object> getAllAccountTypes(){
        return AppResponse.generateResponse("all_data", HttpStatus.OK, service.getAllAccounts() , true);
    }
    @GetMapping("/{id}")
    public Optional<Account> getAccountById(@PathVariable Long id){
        return service.getAccountById(id);
    }

    @PostMapping("/save")
    public Account saveAccount(@RequestBody Account account){
        return service.saveAccount(account );
    }

    @PutMapping("/update")
    public Account updateAccount(@RequestBody Account account){
        return service.saveAccount(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        if(service.getAccountById(id).isPresent()){
            service.deleteAccount(id);
            return new ResponseEntity<>("deleted Successfully", HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("We cannot find account Id : "+id,HttpStatus.BAD_REQUEST);
        }
    }


}
