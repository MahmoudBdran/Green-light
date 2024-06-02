package com.erp.greenlight.services;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.InvItemcard;
import com.erp.greenlight.repositories.AccountRepo;
import com.erp.greenlight.repositories.InvItemCardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepo repo;

    public List<Account> getAllAccounts(){

        return repo.findAll();
    }

    public Optional<Account> getAccountById(@PathVariable Long id){
        return Optional.of(repo.findById(id).get());
    }
    public Account saveAccount(Account account){
        return repo.save(account);
    }
    public void deleteAccount( Long id){
        repo.deleteById(id);
    }
}
