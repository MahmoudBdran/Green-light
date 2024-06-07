package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.CustomerDto;
import com.erp.greenlight.enums.StartBalanceStatusEnum;
import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.AccountType;
import com.erp.greenlight.models.Customer;
import com.erp.greenlight.models.Supplier;
import com.erp.greenlight.repositories.AccountRepo;
import com.erp.greenlight.repositories.AdminPanelSettingsRepo;
import com.erp.greenlight.repositories.CustomerRepo;
import com.erp.greenlight.repositories.SupplierRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    SupplierRepo supplierRepo;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    private AdminPanelSettingsRepo adminPanelSettingsRepo;
    public List<Supplier> getAllSuppliers(){
        return  supplierRepo.findAll();
    }

    public Optional<Supplier> getSupplierById(@PathVariable Long id){
        if(supplierRepo.findById(id).isPresent()){
            return Optional.of(supplierRepo.findById(id).orElseThrow());
        }else{
            return Optional.empty();
        }
    }


    @Transactional
    public Supplier saveSupplier(Supplier supplier){
        Supplier savedSupplier =new Supplier();
        if(validateSupplierInDB(supplier)){
            if(supplier.getStartBalanceStatus()== StartBalanceStatusEnum.CREDIT.getValue()){
                //credit
                supplier.setStartBalance(supplier.getStartBalance().negate());
            }else if(supplier.getStartBalanceStatus()==StartBalanceStatusEnum.DEBIT.getValue()){
                supplier.setStartBalance(supplier.getStartBalance());
                if(supplier.getStartBalance().compareTo(BigDecimal.ZERO)<0){
                    supplier.setStartBalance(supplier.getStartBalance().negate());
                }
            }else if(supplier.getStartBalanceStatus()==StartBalanceStatusEnum.BALANCED.getValue()){
                supplier.setStartBalance(BigDecimal.ZERO);
            }else{
                supplier.setStartBalanceStatus(StartBalanceStatusEnum.BALANCED.getValue());
                supplier.setStartBalance(BigDecimal.ZERO);
            }
            supplier.setCurrentBalance(supplier.getStartBalance());


            supplier.setAccountNumber( new Account(initiateAccountForSupplier(supplier).getId() ));

            savedSupplier = supplierRepo.save(supplier);
            return savedSupplier;
        }else{
            return null;
        }

    }

    public boolean validateSupplierInDB(Supplier supplier){
        if(supplierRepo.findByName(supplier.getName())==null){
            return true;
        }else{
            return false;
        }
    }
    public Account initiateAccountForSupplier(Supplier supplier){
        Account account = new Account();
        account.setName(supplier.getName());
        account.setStartBalanceStatus(supplier.getStartBalanceStatus());

        //start Balance status , Start Balance
        if(supplier.getStartBalanceStatus()== StartBalanceStatusEnum.CREDIT.getValue()){
            //credit
            account.setStartBalance(supplier.getStartBalance().negate());
        }else if(supplier.getStartBalanceStatus()==StartBalanceStatusEnum.DEBIT.getValue()){
            account.setStartBalance(supplier.getStartBalance());
            if(account.getStartBalance().compareTo(BigDecimal.ZERO)<0){
                account.setStartBalance(account.getStartBalance().negate());
            }
        }else if(supplier.getStartBalanceStatus()==StartBalanceStatusEnum.BALANCED.getValue()){
            account.setStartBalance(BigDecimal.ZERO);
        }else{
            account.setStartBalanceStatus(StartBalanceStatusEnum.BALANCED.getValue());
            account.setStartBalance(BigDecimal.ZERO);
        }


        account.setCurrentBalance(supplier.getStartBalance());
        account.setParentAccount(new Account(adminPanelSettingsRepo.findAll().get(0).getCustomerParentAccountNumber()));
        account.setNotes(supplier.getNotes());
        account.setIsParent(false);
        AccountType accountType=new AccountType();
        accountType.setId(3L);
        account.setAccountType(accountType);
        account.setActive(true);

        return accountRepo.save(account);

    }
    public void deleteSupplier( Long id){
        supplierRepo.deleteById(id);
    }
}
