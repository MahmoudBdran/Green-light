package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.InvoiceItemDTO;
import com.erp.greenlight.models.SupplierOrder;
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
@RequestMapping("/supplierOrderDetails")
@CrossOrigin(origins = "http://localhost:4200")
public class SupplierOrderDetailsController {

    @Autowired
    private SupplierOrderDetailsService supplierOrderDetailsService;

    @Autowired
    private InvItemCardService invItemCardService;

    @Autowired
    private InvUomService invUomService;
    @Autowired
    private SupplierOrderService supplierOrderService;

    @GetMapping("/{id}")
    public ResponseEntity<Object>  getSupplierOrderDetails(@PathVariable Long id){
        Map<String, Object> data = new HashMap<>();

        data.put("supplierOrderDetails", supplierOrderDetailsService.findByOrderId(id));
        data.put("invItems", invItemCardService.getAllInvItemCards());
        data.put("invUoms",invUomService.getAllInvUoms());

        return AppResponse.generateResponse("all_data", HttpStatus.OK,  supplierOrderDetailsService.findByOrderId(id) , true);
    }
    @PostMapping("/saveItemInOrder")
    public ResponseEntity<Object> saveItemInOrder(@RequestBody InvoiceItemDTO invoiceItemDTO) throws JsonProcessingException {
        return AppResponse.generateResponse("all_data", HttpStatus.OK,  supplierOrderService.saveItemInOrder(invoiceItemDTO) , true);
    }
    @PutMapping("/updateItemInOrder")
    public ResponseEntity<Object> updateItemInOrder(@RequestBody InvoiceItemDTO invoiceItemDTO) throws JsonProcessingException {
        return AppResponse.generateResponse("all_data", HttpStatus.OK,  supplierOrderService.updateItemInOrder(invoiceItemDTO) , true);
    }


}
