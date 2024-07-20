package com.erp.greenlight.controllers;

import com.erp.greenlight.models.InvItemcardCategory;
import com.erp.greenlight.services.InvItemCardCategoryService;
import com.erp.greenlight.services.InvItemCardService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inv_itemcard_categories")
@CrossOrigin(origins = "http://localhost")

public class InvItemCardCategoryController {

    @Autowired
    InvItemCardCategoryService service;

    @GetMapping
    public ResponseEntity<Object> getAllInvItemCards(){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,service.getAllInvItemCardCategories(), true);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getInvItemCardById(@PathVariable Long id){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,service.getInvItemCardCategoryById(id), true);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveInvItemCard(@RequestBody InvItemcardCategory invItemcardCategory){
        return AppResponse.generateResponse("تم حفظ المنتج بنجاح", HttpStatus.OK,service.saveInvItemCardCategory(invItemcardCategory), true);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateInvItemCard(@RequestBody InvItemcardCategory invItemcardCategory){
        return AppResponse.generateResponse("تم تحديث المنتج بنجاح", HttpStatus.OK,service.saveInvItemCardCategory(invItemcardCategory), true);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInvItemCardCategory(@PathVariable Long id){
        if(service.getInvItemCardCategoryById(id).isPresent()){
            service.deleteInvItemCardCategory(id);
            return AppResponse.generateResponse("تم حذف المنتج بنجاح", HttpStatus.OK,null, true);

        }else{
            return AppResponse.generateResponse("المنتج غير موجود بالفعل", HttpStatus.BAD_REQUEST,null,false);

        }
    }


}
