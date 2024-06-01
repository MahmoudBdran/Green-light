package com.erp.greenlight.controllers;

import com.erp.greenlight.models.Customer;
import com.erp.greenlight.models.InvUom;
import com.erp.greenlight.services.CustomerService;
import com.erp.greenlight.services.InvUomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/InvUom")
public class InvUomController {

    @Autowired
    InvUomService service;

    @GetMapping
    public List<InvUom> getAllInvUom(){
        return service.getAllInvUoms();
    }
    @GetMapping("/{id}")
    public Optional<InvUom> getInvUomById(@PathVariable Long id){
        return service.getInvUomById(id);
    }

    @PostMapping("/save")
    public InvUom saveInvUom(@RequestBody InvUom invUom){
        return service.saveInvUom(invUom);
    }

    @PutMapping("/update")
    public InvUom updateInvUom(@RequestBody InvUom invUom){
        return service.saveInvUom(invUom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvUom(@PathVariable Long id){
        if(service.getInvUomById(id).isPresent()){
            service.deleteInvUom(id);
            return new ResponseEntity<>("deleted Successfully", HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("We cannot find Inventory Uom Id : "+id,HttpStatus.BAD_REQUEST);

        }
    }


}
