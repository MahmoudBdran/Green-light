package com.erp.greenlight.services;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.AdminPanelSettings;
import com.erp.greenlight.models.Customer;
import com.erp.greenlight.models.Supplier;
import com.erp.greenlight.repositories.AdminPanelSettingsRepo;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AdminPanelSettingsService {
    @Autowired
    AdminPanelSettingsRepo adminPanelSettingsRepo;
    public AdminPanelSettings getAllAdminPanelSettings(){
        return  adminPanelSettingsRepo.findAll().get(0);
    }

    public AdminPanelSettings updateAdminPanelSettings(@RequestBody AdminPanelSettings adminPanelSettings){

        Integer adminPanelSettingsId = adminPanelSettings.getId();
        AdminPanelSettings existingAdminPanelSettings = adminPanelSettingsRepo.findById(adminPanelSettingsId).orElseThrow();

        existingAdminPanelSettings.setSystemName(adminPanelSettings.getSystemName() != null ? adminPanelSettings.getSystemName() : existingAdminPanelSettings.getSystemName());
        existingAdminPanelSettings.setPhoto(adminPanelSettings.getPhoto() != null ? adminPanelSettings.getPhoto() : existingAdminPanelSettings.getPhoto());
        existingAdminPanelSettings.setActive(adminPanelSettings.getActive() != null ? adminPanelSettings.getActive() : existingAdminPanelSettings.getActive());
        existingAdminPanelSettings.setGeneralAlert(adminPanelSettings.getGeneralAlert() != null ? adminPanelSettings.getGeneralAlert() : existingAdminPanelSettings.getGeneralAlert());

        existingAdminPanelSettings.setAddress(adminPanelSettings.getAddress() != null ? adminPanelSettings.getAddress() : existingAdminPanelSettings.getAddress());
        existingAdminPanelSettings.setPhone(adminPanelSettings.getPhone() != null ? adminPanelSettings.getPhone() : existingAdminPanelSettings.getPhone());
        existingAdminPanelSettings.setCustomerParentAccountNumber(adminPanelSettings.getCustomerParentAccountNumber() != null ? adminPanelSettings.getCustomerParentAccountNumber() : existingAdminPanelSettings.getCustomerParentAccountNumber());
        existingAdminPanelSettings.setSuppliersParentAccountNumber(adminPanelSettings.getSuppliersParentAccountNumber() != null ? adminPanelSettings.getSuppliersParentAccountNumber() : existingAdminPanelSettings.getSuppliersParentAccountNumber());
        existingAdminPanelSettings.setEmployeesParentAccountNumber(adminPanelSettings.getEmployeesParentAccountNumber() != null ? adminPanelSettings.getEmployeesParentAccountNumber() : existingAdminPanelSettings.getEmployeesParentAccountNumber());
        existingAdminPanelSettings.setNotes(adminPanelSettings.getNotes() != null ? adminPanelSettings.getNotes() : existingAdminPanelSettings.getNotes());


        return  adminPanelSettingsRepo.save(existingAdminPanelSettings)   ;
    }



}
