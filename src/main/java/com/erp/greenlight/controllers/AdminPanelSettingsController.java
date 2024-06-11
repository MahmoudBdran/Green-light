package com.erp.greenlight.controllers;

import com.erp.greenlight.models.AdminPanelSettings;
import com.erp.greenlight.services.AccountService;
import com.erp.greenlight.services.AdminPanelSettingsService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adminPanelSettings")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminPanelSettingsController {

    @Autowired
    private AdminPanelSettingsService adminPanelSettingsService;
    @Autowired
    private AccountService accountService;

    @GetMapping()
    public ResponseEntity<Object> getAllAdminPanelSettings() {

        Map<String, Object> data = new HashMap<>();

        data.put("adminSettings",  adminPanelSettingsService.getAllAdminPanelSettings() );
        data.put("parentAccounts",  accountService.getParentAccounts());

        return AppResponse.generateResponse("system data", HttpStatus.OK, data , true);

    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateAdminPanelSettings(@RequestBody AdminPanelSettings adminPanelSettings) {
        return AppResponse.generateResponse("تم تحديث معلومات الإداره بنجاح", HttpStatus.OK, adminPanelSettingsService.updateAdminPanelSettings(adminPanelSettings), true);

    }
}
