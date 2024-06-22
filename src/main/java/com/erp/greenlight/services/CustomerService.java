package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.CustomerDto;
import com.erp.greenlight.enums.StartBalanceStatusEnum;
import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.AccountType;
import com.erp.greenlight.models.AdminPanelSettings;
import com.erp.greenlight.models.Customer;
import com.erp.greenlight.repositories.AccountRepo;
import com.erp.greenlight.repositories.AdminPanelSettingsRepo;
import com.erp.greenlight.repositories.CustomerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo repo;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    private AdminPanelSettingsRepo adminPanelSettingsRepo;

    public List<CustomerDto> getAllCustomers(){
        List<Customer> customers= repo.findAll();
        List<CustomerDto> customerDtos= new ArrayList<>();
        for(Customer customer : customers){
            CustomerDto customerDto = CustomerDto.convertToDto(customer);
            customerDtos.add(customerDto);
        }

        return customerDtos;
    }

    public Optional<Customer> getCustomerById(@PathVariable Long id){
        if(repo.findById(id).isPresent()){
            return Optional.of(repo.findById(id).get());
        }else{
            return Optional.empty();
        }
    }

    @Transactional
    public Customer saveCustomer(Customer customer){
        if(validateCustomerInDB(customer)){
            if(customer.getStartBalanceStatus()== StartBalanceStatusEnum.CREDIT.getValue()){
                //credit
                customer.setStartBalance(customer.getStartBalance().negate());
            }else if(customer.getStartBalanceStatus()==StartBalanceStatusEnum.DEBIT.getValue()){
                customer.setStartBalance(customer.getStartBalance());
                if(customer.getStartBalance().compareTo(BigDecimal.ZERO)<0){
                    customer.setStartBalance(customer.getStartBalance().negate());
                }
            }else if(customer.getStartBalanceStatus()==StartBalanceStatusEnum.BALANCED.getValue()){
                customer.setStartBalance(BigDecimal.ZERO);
            }else{
                customer.setStartBalanceStatus(StartBalanceStatusEnum.BALANCED.getValue());
                customer.setStartBalance(BigDecimal.ZERO);
            }
            customer.setCurrentBalance(customer.getStartBalance());


            customer.setAccount( new Account(initiateAccountForCustomer(customer).getId() ));

            Customer savedCustomer = repo.save(customer);
            return savedCustomer;
        }else{
            return null;
        }
        //when saving customer we need to add account for him at the same time

    }

    public boolean validateCustomerInDB(Customer customer){
        if(repo.findByName(customer.getName())==null){
            return true;
        }else{
            return false;
        }
    }

    public Account initiateAccountForCustomer(Customer customer){
        Account account = new Account();
        account.setName(customer.getName());
        account.setStartBalanceStatus(customer.getStartBalanceStatus());

        //start Balance status , Start Balance
        if(customer.getStartBalanceStatus()== StartBalanceStatusEnum.CREDIT.getValue()){
            //credit
            account.setStartBalance(customer.getStartBalance().negate());
        }else if(customer.getStartBalanceStatus()==StartBalanceStatusEnum.DEBIT.getValue()){
            account.setStartBalance(customer.getStartBalance());
            if(account.getStartBalance().compareTo(BigDecimal.ZERO)<0){
                account.setStartBalance(account.getStartBalance().negate());
            }
        }else if(customer.getStartBalanceStatus()==StartBalanceStatusEnum.BALANCED.getValue()){
            account.setStartBalance(BigDecimal.ZERO);
        }else{
            account.setStartBalanceStatus(StartBalanceStatusEnum.BALANCED.getValue());
            account.setStartBalance(BigDecimal.ZERO);
        }


        account.setCurrentBalance(customer.getStartBalance());
        account.setParentAccount(new Account(adminPanelSettingsRepo.findAll().get(0).getCustomerParentAccountNumber()));
        account.setNotes(customer.getNotes());
        account.setIsParent(false);
         AccountType accountType=new AccountType();
        accountType.setId(3L);
        account.setAccountType(accountType);
        account.setActive(true);

       return accountRepo.save(account);

    }
    public void deleteCustomer( Long id){
        repo.deleteById(id);
    }
}
