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
@CrossOrigin(origins = {"http://localhost", "https://animated-sprinkles-d16b69.netlify.app"})

@RequestMapping("/supplierOrderReturnDetails")
 
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

        return AppResponse.generateResponse("all_data", HttpStatus.OK,  data , true);
    }
    @PostMapping("/saveItemInOrderReturn")
    public ResponseEntity<Object> saveItemInOrderReturn(@RequestBody ReturnInvoiceItemDTO invoiceItemDTO) throws JsonProcessingException {
        if(supplierOrderReturnDetailsService.checkItemInOrder(invoiceItemDTO)){
            return AppResponse.generateResponse("عفوا الصنف موجود بالفعل بالفاتورة!!", HttpStatus.OK,  null, true);
        }else{
            return AppResponse.generateResponse("تم اضافة الصنف في الفاتورة", HttpStatus.OK,  supplierOrderReturnDetailsService.saveItemInOrderReturn(invoiceItemDTO) , true);
        }
    }

    @DeleteMapping("/deleteItemInOrderReturn/{id}")
    public ResponseEntity<Object> deleteItemInOrder(@PathVariable Long id){
        if(supplierOrderReturnDetailsService.checkOrderDetailsItemIsApproved(id)){
            return AppResponse.generateResponse("تعذر حذف المنتج من الفاتوره لأنها مغلقه", HttpStatus.OK,null , true);
        }
        return AppResponse.generateResponse("تم حذف الصنف من الفاتورة", HttpStatus.OK,  supplierOrderReturnDetailsService.deleteItemFromSupplierOrderReturn(id) , true);
    }


}
