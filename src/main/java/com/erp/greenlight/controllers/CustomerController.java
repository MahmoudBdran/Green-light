package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.CustomerDto;
import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.Customer;
import com.erp.greenlight.repositories.CustomerRepo;
import com.erp.greenlight.services.AccountService;
import com.erp.greenlight.services.CustomerService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:4200")

public class CustomerController {

    @Autowired
    CustomerService service;
    @Autowired
    AccountService accountService;
    @Autowired
    private CustomerRepo customerRepo;

    @GetMapping
    public ResponseEntity<Object> getAllCustomers(){
         return AppResponse.generateResponse("all_data", HttpStatus.OK, service.getAllCustomers() , true);
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable Long id){
        return service.getCustomerById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> saveCustomer(@RequestBody Customer customer){
        Customer savedCustomer = service.saveCustomer(customer);
        if(savedCustomer==null){
            return AppResponse.generateResponse("لم يتم حفظ الحساب لوجود الحساب بالفعل", HttpStatus.BAD_REQUEST, savedCustomer, false);

        }else{
            return AppResponse.generateResponse("تم حفط الحساب بنجاح", HttpStatus.OK, savedCustomer, true);
        }

    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateCustomer(@RequestBody Customer customer){
        Long customerId = customer.getId();
        Customer existingCustomer = customerRepo.findById(customerId).get();
        existingCustomer.setName(customer.getName() != null ? customer.getName() : existingCustomer.getName());
        existingCustomer.setCustomerCode(customer.getCustomerCode() != null ? customer.getCustomerCode() : existingCustomer.getCustomerCode());
        existingCustomer.setAccountNumber(customer.getAccountNumber() != null ? customer.getAccountNumber() : existingCustomer.getAccountNumber());
        existingCustomer.setStartBalanceStatus(customer.getStartBalanceStatus() !=0 ? customer.getStartBalanceStatus() : existingCustomer.getStartBalanceStatus());
        existingCustomer.setStartBalance(customer.getStartBalance() != null ? customer.getStartBalance() : existingCustomer.getStartBalance());
        existingCustomer.setCurrentBalance(customer.getCurrentBalance() != null ? customer.getCurrentBalance() : existingCustomer.getCurrentBalance());
        existingCustomer.setNotes(customer.getNotes() != null ? customer.getNotes() : existingCustomer.getNotes());
        existingCustomer.setUpdatedBy(customer.getUpdatedBy() != null ? customer.getUpdatedBy() : existingCustomer.getUpdatedBy());
        existingCustomer.setUpdatedAt(LocalDateTime.now()); // Update timestamp regardless// Maintain existing active state if not provided
        if(customer.isActive()==true){
            existingCustomer.setActive(customer.isActive());
        }else if(customer.isActive()==false){
            existingCustomer.setActive(customer.isActive());
        }
        existingCustomer.setAddress(customer.getAddress() != null ? customer.getAddress() : existingCustomer.getAddress());
        existingCustomer.setPhones(customer.getPhones() != null ? customer.getPhones() : existingCustomer.getPhones());

//        existingCustomer.setCustomerCode(customer.getCustomerCode());
//        existingCustomer.setAccountNumber(existingCustomer.getAccountNumber());
//        existingCustomer.setStartBalanceStatus(customer.getStartBalanceStatus());
//        existingCustomer.setStartBalance(customer.getStartBalance());
//        existingCustomer.setCurrentBalance(customer.getCurrentBalance());
//        existingCustomer.setNotes(customer.getNotes());
//        existingCustomer.setUpdatedBy(customer.getUpdatedBy());
//        existingCustomer.setUpdatedAt(LocalDateTime.now());
//        existingCustomer.setActive(customer.isActive());
//        existingCustomer.setComCode(customer.getComCode());
//        existingCustomer.setAddress(customer.getAddress());
//        existingCustomer.setPhones(customer.getPhones());
        Customer savedCustomer =customerRepo.save(existingCustomer);
        return AppResponse.generateResponse("تم تحديث الحساب بنجاح", HttpStatus.OK, savedCustomer, true);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id){
        if(service.getCustomerById(id).isPresent()){
            service.deleteCustomer(id);
            return AppResponse.generateResponse("تم حذف الحساب بنجاح", HttpStatus.OK,"deleted" , true);
        }else{
            return AppResponse.generateResponse("لم نتمكن من ايجاد الحساب", HttpStatus.BAD_REQUEST, "ERROR", true);

        }
    }


}
