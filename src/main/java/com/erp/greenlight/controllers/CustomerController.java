package com.erp.greenlight.controllers;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.Customer;
import com.erp.greenlight.services.AccountService;
import com.erp.greenlight.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService service;

    @GetMapping
    public List<Customer> getAllCustomers(){
        return service.getAllCustomers();
    }
    @GetMapping("/{id}")
    public Optional<Customer> getAccountById(@PathVariable Long id){
        return service.getCustomerById(id);
    }

    @PostMapping("/save")
    public Customer saveCustomer(@RequestBody Customer customer){
        return service.saveCustomer(customer);
    }

    @PutMapping("/update")
    public Customer updateCustomer(@RequestBody Customer account){
        return service.saveCustomer(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
        if(service.getCustomerById(id).isPresent()){
            service.deleteCustomer(id);
            return new ResponseEntity<>("deleted Successfully", HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("We cannot find customer Id : "+id,HttpStatus.BAD_REQUEST);

        }
    }


}
