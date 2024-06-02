package com.erp.greenlight.controllers;

import com.erp.greenlight.models.InvItemcard;
import com.erp.greenlight.services.InvItemCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itemcards")
public class InvItemCardController {

    @Autowired
    InvItemCardService service;

    @GetMapping
    public List<InvItemcard> getAllInvItemCards(){
        return service.getAllInvItemCards();
    }
    @GetMapping("/{id}")
    public Optional<InvItemcard> getInvItemCardById(@PathVariable Long id){
        return service.getInvItemCardById(id);
    }

    @PostMapping("/save")
    public InvItemcard saveInvItemCard(@RequestBody InvItemcard invItemcard){
        return service.saveInvItemCard(invItemcard);
    }

    @PutMapping("/update")
    public InvItemcard updateInvItemCard(@RequestBody InvItemcard invItemcard){
        return service.saveInvItemCard(invItemcard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvItemCard(@PathVariable Long id){
        if(service.getInvItemCardById(id).isPresent()){
            service.deleteInvItemCard(id);
            return new ResponseEntity<>("deleted Successfully", HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("We cannot find Id : "+id,HttpStatus.BAD_REQUEST);

        }
    }


}
