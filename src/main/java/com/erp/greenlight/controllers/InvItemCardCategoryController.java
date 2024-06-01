package com.erp.greenlight.controllers;

import com.erp.greenlight.models.InvItemcard;
import com.erp.greenlight.models.InvItemcardCategory;
import com.erp.greenlight.services.InvItemCardCategoryService;
import com.erp.greenlight.services.InvItemCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inv_itemcard_categories")
public class InvItemCardCategoryController {

    @Autowired
    InvItemCardCategoryService service;

    @GetMapping
    public List<InvItemcardCategory> getAllInvItemCards(){
        return service.getAllInvItemCardCategories();
    }
    @GetMapping("/{id}")
    public Optional<InvItemcardCategory> getInvItemCardById(@PathVariable Long id){
        return service.getInvItemCardCategoryById(id);
    }

    @PostMapping("/save")
    public InvItemcardCategory saveInvItemCard(@RequestBody InvItemcardCategory invItemcardCategory){
        return service.saveInvItemCardCategory(invItemcardCategory);
    }

    @PutMapping("/update")
    public InvItemcardCategory updateInvItemCard(@RequestBody InvItemcardCategory invItemcardCategory){
        return service.saveInvItemCardCategory(invItemcardCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvItemCardCategory(@PathVariable Long id){
        if(service.getInvItemCardCategoryById(id).isPresent()){
            service.deleteInvItemCardCategory(id);
            return new ResponseEntity<>("deleted Successfully", HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("We cannot find Id : "+id,HttpStatus.BAD_REQUEST);

        }
    }


}
