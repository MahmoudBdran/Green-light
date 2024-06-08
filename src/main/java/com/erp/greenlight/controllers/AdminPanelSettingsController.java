package com.erp.greenlight.controllers;

import com.erp.greenlight.models.AdminPanelSettings;
import com.erp.greenlight.services.AdminPanelSettingsService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminPanelSettings")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminPanelSettingsController {

    @Autowired
    private AdminPanelSettingsService adminPanelSettingsService;

    @GetMapping()
    public ResponseEntity<Object> getAllAdminPanelSettings() {
       // return adminPanelSettingsService.getAllAdminPanelSettings();
        return AppResponse.generateResponse("تم تحديث معلومات الإداره بنجاح", HttpStatus.OK, adminPanelSettingsService.getAllAdminPanelSettings(), true);

    }

    @GetMapping("/update")
    public ResponseEntity<Object> updateAdminPanelSettings(@RequestBody AdminPanelSettings adminPanelSettings) {
        return AppResponse.generateResponse("تم تحديث معلومات الإداره بنجاح", HttpStatus.OK, adminPanelSettingsService.updateAdminPanelSettings(adminPanelSettings), true);

    }
}
