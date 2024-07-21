package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.CustomerDto;
import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.Customer;
import com.erp.greenlight.repositories.AccountRepo;
import com.erp.greenlight.repositories.CustomerRepo;
import com.erp.greenlight.services.AccountService;
import com.erp.greenlight.services.CustomerService;
import com.erp.greenlight.utils.AppResponse;
import jakarta.transaction.Transactional;
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
@CrossOrigin(origins = "http://localhost")

public class CustomerController {

    @Autowired
    CustomerService service;
    @Autowired
    AccountService accountService;
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    AccountRepo accountRepo;

    @GetMapping
    public ResponseEntity<Object> getAllCustomers(){
         return AppResponse.generateResponse("all_data", HttpStatus.OK, service.getAllCustomers() , true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable Long id){
        return AppResponse.generateResponse("all_data", service.getCustomerById(id) .isEmpty()?HttpStatus.BAD_REQUEST:HttpStatus.OK, service.getCustomerById(id) , service.getCustomerById(id) .isEmpty()?false:true);

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
    @Transactional
    public ResponseEntity<Object> updateCustomer(@RequestBody Customer customer){

        Long customerId = customer.getId();
        Customer existingCustomer = customerRepo.findById(customerId).orElseThrow();

        existingCustomer.setName(customer.getName() != null ? customer.getName() : existingCustomer.getName());
        existingCustomer.setAddress(customer.getAddress() != null ? customer.getAddress() : existingCustomer.getAddress());
        existingCustomer.setPhones(customer.getPhones() != null ? customer.getPhones() : existingCustomer.getPhones());
        existingCustomer.setNotes(customer.getNotes() != null ? customer.getNotes() : existingCustomer.getNotes());

        // Update timestamp regardless// Maintain existing active state if not provided
        if(customer.isActive()==true){
            existingCustomer.setActive(customer.isActive());
        }else if(customer.isActive()==false){
            existingCustomer.setActive(customer.isActive());
        }

        Customer savedCustomer =customerRepo.save(existingCustomer);

        Account existingAccount = savedCustomer.getAccount();
        existingAccount.setName(customer.getName());
        accountRepo.save(existingAccount);
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
