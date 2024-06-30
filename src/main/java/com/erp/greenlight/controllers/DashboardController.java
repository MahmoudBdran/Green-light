package com.erp.greenlight.controllers;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.repositories.AdminPanelSettingsRepo;
import com.erp.greenlight.services.AccountService;
import com.erp.greenlight.services.AccountTypeService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:4200")

public class DashboardController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountTypeService accountTypeService;
    @Autowired
    AdminPanelSettingsRepo adminPanelSettingsRepo;

    @GetMapping
    public ResponseEntity<Object> getData(){

        Map<String, Object> data = new HashMap<>();

        data.put("accounts",  accountService.getAllAccounts());
        data.put("accountTypes",  accountTypeService.getAllAccountTypes());
        data.put("parentAccounts",  accountService.getParentAccounts());

        return AppResponse.generateResponse("all_data", HttpStatus.OK, data , true);
    }


 


}
