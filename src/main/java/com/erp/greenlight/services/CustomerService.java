package com.erp.greenlight.services;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.Customer;
import com.erp.greenlight.repositories.AccountRepo;
import com.erp.greenlight.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo repo;

    public List<Customer> getAllCustomers(){
        return repo.findAll();
    }

    public Optional<Customer> getCustomerById(@PathVariable Long id){
        return Optional.of(repo.findById(id).get());
    }
    public Customer saveCustomer(Customer customer){
        return repo.save(customer);
    }
    public void deleteCustomer( Long id){
        repo.deleteById(id);
    }
}
