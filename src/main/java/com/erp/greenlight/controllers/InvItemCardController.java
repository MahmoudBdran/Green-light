package com.erp.greenlight.controllers;

import com.erp.greenlight.models.InvItemCard;
import com.erp.greenlight.services.InvItemCardCategoryService;
import com.erp.greenlight.services.InvItemCardService;
import com.erp.greenlight.services.InvUomService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/itemcards")

@CrossOrigin(origins = "http://localhost")

public class InvItemCardController {

    @Autowired
    InvItemCardService service;

    @Autowired
    InvItemCardCategoryService invItemCardCategoryService;

    @Autowired
    InvUomService invUomService;

    @GetMapping
    public ResponseEntity<Object> getAllInvItemCards(){

        Map<String, Object> data = new HashMap<>();

        data.put("invItems",  service.getAllInvItemCards());
        data.put("invItemsCategories",  invItemCardCategoryService.getAllInvItemCardCategories());

        data.put("invUomsParent",  invUomService.invUomsParent());
        data.put("invUomsChilds",  invUomService.invUomsChilds());


        return AppResponse.generateResponse("all_data", HttpStatus.OK, data, true);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getInvItemCardById(@PathVariable Long id){

        return AppResponse.generateResponse("all_data", HttpStatus.OK, service.getInvItemCardById(id) , true);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveInvItemCard(@RequestBody InvItemCard invItemcard){
        return AppResponse.generateResponse("تم الحفظ", HttpStatus.OK,  service.saveInvItemCard(invItemcard) , true);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateInvItemCard(@RequestBody InvItemCard invItemcard){

        return AppResponse.generateResponse("تم التحديث", HttpStatus.OK,   service.updateInvItemCard(invItemcard) , true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInvItemCard(@PathVariable Long id){
        if(service.getInvItemCardById(id).isPresent()){
            service.deleteInvItemCard(id);
            return AppResponse.generateResponse("تم حذف المنتج بنجاح", HttpStatus.OK,null, true);
        }else{
            return AppResponse.generateResponse("المنتج غير موجود بالفعل", HttpStatus.BAD_REQUEST,null,false);

        }
    }


}
