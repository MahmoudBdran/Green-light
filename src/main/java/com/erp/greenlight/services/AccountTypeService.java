package com.erp.greenlight.services;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.AccountType;
import com.erp.greenlight.repositories.AccountRepo;
import com.erp.greenlight.repositories.AccountTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class AccountTypeService {
    @Autowired
    AccountTypeRepo repo;

    public List<AccountType> getAllAccountTypes(){
        return repo.findAll();
    }

    public Optional<AccountType> getAccountTypeById(@PathVariable Long id){
        return Optional.of(repo.findById(id).get());
    }
    public AccountType saveAccountType(AccountType accountType){
        return repo.save(accountType);
    }
    public void deleteAccountType( Long id){
        repo.deleteById(id);
    }
}
