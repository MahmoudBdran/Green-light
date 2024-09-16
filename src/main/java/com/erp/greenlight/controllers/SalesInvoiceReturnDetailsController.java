package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.InvoiceItemDTO;
import com.erp.greenlight.DTOs.SalesInvoiceItemDTO;
import com.erp.greenlight.DTOs.SalesReturnInvoiceItemDTO;
import com.erp.greenlight.services.*;
import com.erp.greenlight.utils.AppResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost", "https://animated-sprinkles-d16b69.netlify.app"})

@RequestMapping("/salesInvoiceReturnDetails")
 
public class SalesInvoiceReturnDetailsController {

    @Autowired
    private SalesInvoiceReturnDetailsService salesInvoiceReturnDetailsService;

    @Autowired
    private InvItemCardService invItemCardService;

    @Autowired
    private InvUomService invUomService;

    @GetMapping("/{id}")
    public ResponseEntity<Object>  getSalesInvoiceDetails(@PathVariable Long id){
        Map<String, Object> data = new HashMap<>();

        data.put("salesInvoiceReturnDetails", salesInvoiceReturnDetailsService.findBySalesInvoiceReturnId(id));
        data.put("invItems", invItemCardService.getAllInvItemCards());
        data.put("invUoms",invUomService.getAllInvUoms());

        //return AppResponse.generateResponse("all_data", HttpStatus.OK,  salesInvoiceReturnDetailsService.findBySalesInvoiceId(id) , true);
        return AppResponse.generateResponse("all_data", HttpStatus.OK,  data , true);
    }
    @PostMapping("/saveItemInSalesInvoiceReturn")
    public ResponseEntity<Object> saveItemInOrder(@RequestBody SalesReturnInvoiceItemDTO invoiceItemDTO) throws JsonProcessingException {
        System.out.println("entered saveItemInOrder");
        if(salesInvoiceReturnDetailsService.checkItemInOrderOrNot(invoiceItemDTO)){
            System.out.println("entered if cond true checkItemInOrderOrNot ");
            return AppResponse.generateResponse("تم التعديل علي الصنف في الفاتورة لأنة موجود بالفعل!", HttpStatus.OK, salesInvoiceReturnDetailsService.overWriteItemInOrder(invoiceItemDTO) , true);
        }else{
            System.out.println("entered if cond false checkItemInOrderOrNot ");
            return AppResponse.generateResponse("تم اضافة الصنف في الفاتورة", HttpStatus.OK,  salesInvoiceReturnDetailsService.saveItemInOrder(invoiceItemDTO) , true);
        }
    }

    @DeleteMapping("/deleteItemInOrderReturn/{id}")
    public ResponseEntity<Object> deleteItemInOrderReturn(@PathVariable Long id){
        if(salesInvoiceReturnDetailsService.checkOrderDetailsItemIsApproved(id)){
            return AppResponse.generateResponse("تعذر حذف المنتج من الفاتوره لأنها مغلقه", HttpStatus.BAD_REQUEST,null , false);
        }
        return AppResponse.generateResponse("تم حذف الصنف من الفاتورة", HttpStatus.OK,  salesInvoiceReturnDetailsService.deleteItemFromSalesInvoiceReturn(id) , true);
    }


}
