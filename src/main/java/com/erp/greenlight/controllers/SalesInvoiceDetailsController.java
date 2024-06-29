package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.GetItemBatchDto;
import com.erp.greenlight.DTOs.InvoiceItemDTO;
import com.erp.greenlight.DTOs.SalesInvoiceItemDTO;
import com.erp.greenlight.models.InvItemCard;
import com.erp.greenlight.models.InvItemCardBatch;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/salesInvoiceDetails")
@CrossOrigin(origins = "http://localhost:4200")
public class SalesInvoiceDetailsController {

    @Autowired
    private SalesInvoiceDetailsService salesInvoiceDetailsService;

    @Autowired
    private InvItemCardService invItemCardService;

    @Autowired
    private InvUomService invUomService;
    @Autowired
    private SalesInvoiceService salesInvoiceService;

    @Autowired
    InvItemCardBatchRepo invItemCardBatchRepo;

    @Autowired
    InvUomRepo invUomRepo;


    @GetMapping("/{id}")
    public ResponseEntity<Object>  getSalesInvoiceDetails(@PathVariable Long id){
        Map<String, Object> data = new HashMap<>();

        data.put("salesInvoiceDetails", salesInvoiceDetailsService.findBySalesInvoiceId(id));
        data.put("invItems", invItemCardService.getAllInvItemCards());
        data.put("invUoms",invUomService.getAllInvUoms());

       // return AppResponse.generateResponse("all_data", HttpStatus.OK,  salesInvoiceDetailsService.findBySalesInvoiceId(id) , true);
        return AppResponse.generateResponse("all_data", HttpStatus.OK,  data , true);
    }

    @PostMapping("/saveItemInSalesInvoice")
    public ResponseEntity<Object> saveItemInOrder(@RequestBody SalesInvoiceItemDTO invoiceItemDTO) throws JsonProcessingException {
        System.out.println("entered saveItemInOrder");
        if(salesInvoiceDetailsService.checkItemInOrderOrNot(invoiceItemDTO)){
            System.out.println("entered if cond true checkItemInOrderOrNot ");
            return AppResponse.generateResponse("all_data", HttpStatus.OK,  salesInvoiceDetailsService.updateItemBeingInsertedAgain(invoiceItemDTO) , true);
        }else{
            System.out.println("entered if cond false checkItemInOrderOrNot ");
            return AppResponse.generateResponse("تم اضافة الصنف في الفاتورة", HttpStatus.OK,  salesInvoiceDetailsService.saveItemInOrder(invoiceItemDTO) , true);
        }
    }
    @PutMapping("/updateItemInSalesInvoice")
    public ResponseEntity<Object> updateItemInOrder(@RequestBody SalesInvoiceItemDTO invoiceItemDTO) throws JsonProcessingException {
        return AppResponse.generateResponse("تم تحديث الصنف في الفاتورة", HttpStatus.OK,  salesInvoiceDetailsService.updateItemInOrder(invoiceItemDTO) , true);
    }

    @DeleteMapping("/deleteItemInOrder/{id}")
    public ResponseEntity<Object> deleteItemInOrder(@PathVariable Long id){
        if(salesInvoiceDetailsService.checkOrderDetailsItemIsApproved(id)){
            return AppResponse.generateResponse("تعذر حذف المنتج من الفاتوره لأنها مغلقه", HttpStatus.BAD_REQUEST,null , false);
        }
        return AppResponse.generateResponse("تم حذف الصنف من الفاتورة", HttpStatus.OK,  salesInvoiceDetailsService.deleteItemFromSalesInvoice(id) , true);
    }





}
