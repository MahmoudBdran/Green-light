package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.InvoiceItemDTO;
import com.erp.greenlight.DTOs.ReturnInvoiceItemDTO;
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
@RequestMapping("/supplierOrderReturnDetails")
@CrossOrigin(origins = "http://localhost:4200")
public class SupplierOrderReturnDetailsController {

    @Autowired
    private SupplierOrderReturnDetailsService supplierOrderReturnDetailsService;

    @Autowired
    private InvItemCardService invItemCardService;

    @Autowired
    private InvUomService invUomService;
    @Autowired
    private SupplierOrderReturnService supplierOrderReturnService;

    @GetMapping("/{id}")
    public ResponseEntity<Object>  getSupplierOrderReturnDetails(@PathVariable Long id){
        Map<String, Object> data = new HashMap<>();

        data.put("supplierOrderReturnDetails", supplierOrderReturnDetailsService.findByOrderReturnId(id));
        data.put("invItems", invItemCardService.getAllInvItemCards());
        data.put("invUoms",invUomService.getAllInvUoms());

       // return AppResponse.generateResponse("all_data", HttpStatus.OK,  supplierOrderDetailsService.findByOrderId(id) , true);
        return AppResponse.generateResponse("all_data", HttpStatus.OK,  data , true);
    }
    @PostMapping("/saveItemInOrderReturn")
    public ResponseEntity<Object> saveItemInOrderReturn(@RequestBody ReturnInvoiceItemDTO invoiceItemDTO) throws JsonProcessingException {
        System.out.println("entered saveItemInOrder");
        if(supplierOrderReturnDetailsService.checkItemInOrderOrNot(invoiceItemDTO)){
            System.out.println("entered if cond true checkItemInOrderOrNot ");
            return AppResponse.generateResponse("تم اضافة الصنف في الفاتورة", HttpStatus.OK,  supplierOrderReturnDetailsService.updateItemBeingInsertedAgain(invoiceItemDTO) , true);
        }else{
            System.out.println("entered if cond false checkItemInOrderOrNot ");
            return AppResponse.generateResponse("تم اضافة الصنف في الفاتورة", HttpStatus.OK,  supplierOrderReturnDetailsService.saveItemInOrderReturn(invoiceItemDTO) , true);
        }
    }
    @PutMapping("/updateItemInOrderReturn")
    public ResponseEntity<Object> updateItemInOrder(@RequestBody ReturnInvoiceItemDTO invoiceItemDTO) throws JsonProcessingException {
        return AppResponse.generateResponse("تم تحديث الصنف في الفاتورة", HttpStatus.OK,  supplierOrderReturnDetailsService.updateItemInOrderReturn(invoiceItemDTO) , true);
    }

    @DeleteMapping("/deleteItemInOrderReturn/{id}")
    public ResponseEntity<Object> deleteItemInOrder(@PathVariable Long id){
        if(supplierOrderReturnDetailsService.checkOrderDetailsItemIsApproved(id)){
            return AppResponse.generateResponse("تعذر حذف المنتج من الفاتوره لأنها مغلقه", HttpStatus.BAD_REQUEST,null , false);
        }
        return AppResponse.generateResponse("تم حذف الصنف من الفاتورة", HttpStatus.OK,  supplierOrderReturnDetailsService.deleteItemFromSupplierOrderReturn(id) , true);
    }


}
