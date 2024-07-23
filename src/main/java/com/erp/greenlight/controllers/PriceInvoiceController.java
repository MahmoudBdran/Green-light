package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.DTOs.SalesInvoiceItemDTO;
import com.erp.greenlight.DTOs.SavePriceInvoiceDTO;
import com.erp.greenlight.DTOs.SavePriceInvoiceDetailsDTO;
import com.erp.greenlight.models.SalesInvoice;
import com.erp.greenlight.repositories.InvItemCardBatchRepo;
import com.erp.greenlight.repositories.InvUomRepo;
import com.erp.greenlight.services.*;
import com.erp.greenlight.utils.AppResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost", "https://animated-sprinkles-d16b69.netlify.app"})

@RequestMapping("/priceInvoice")
 
public class PriceInvoiceController {


    @Autowired
    InvItemCardService invItemCardService;

    @Autowired
    private SalesInvoiceDetailsService salesInvoiceDetailsService;


    @Autowired
    private InvUomService invUomService;

    @Autowired
    PriceInvoiceService priceInvoiceService;

    @GetMapping("")
    public ResponseEntity<Object>  findAll(){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,  priceInvoiceService.findAll() , true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        return AppResponse.generateResponse("all_data", HttpStatus.OK,Optional.of(priceInvoiceService.findById(id)) , true);

    }

    @PostMapping()
    public ResponseEntity<Object> save(@RequestBody SavePriceInvoiceDTO request){
        return AppResponse.generateResponse("تم حفط فاتورة عرض الاسعار بنجاح", HttpStatus.OK, priceInvoiceService.save(request) , true);
    }
    @PutMapping()
    public ResponseEntity<Object> update(@RequestBody SavePriceInvoiceDTO request){
        return AppResponse.generateResponse("تم تحديث فاتورة عرض الاسعار بنجاح", HttpStatus.OK, priceInvoiceService.update(request) , true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){

        return AppResponse.generateResponse("تم حذف الفاتورة بمحتوياتها بنجاح", HttpStatus.OK,  priceInvoiceService.delete(id) , true);
    }

    /*----------    details --------------------------*/


    @GetMapping("/priceInvoiceDetails/{id}")
    public ResponseEntity<Object>  priceInvoiceDetails(@PathVariable Long id){
        Map<String, Object> data = new HashMap<>();

         data.put("invItems", invItemCardService.getAllInvItemCards());
        data.put("invUoms", invUomService.getAllInvUoms());

        return AppResponse.generateResponse("all_data", HttpStatus.OK,  data , true);
    }

    @PostMapping("/saveItem")
    public ResponseEntity<Object> saveItem(@RequestBody SavePriceInvoiceDetailsDTO request) throws JsonProcessingException {
        if(priceInvoiceService.isItemInInvoice(request)){
            return AppResponse.generateResponse("الصنف موجود بالفعل", HttpStatus.OK, null , true);

        }else{
            return AppResponse.generateResponse("تم اضافة الصنف في الفاتورة", HttpStatus.OK,  priceInvoiceService.saveItem(request) , true);
        }
    }

    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable Long id){
        return AppResponse.generateResponse("تم حذف الصنف من الفاتورة", HttpStatus.OK,  priceInvoiceService.deleteItemFromPriceInvoice(id) , true);
    }









}
