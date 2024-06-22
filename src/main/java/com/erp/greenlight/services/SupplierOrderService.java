package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.InvoiceItemDTO;
import com.erp.greenlight.DTOs.SupplierOrderDTO;
import com.erp.greenlight.mappers.SupplierOrderMapper;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.InvItemCardRepo;
import com.erp.greenlight.repositories.InvUomRepo;
import com.erp.greenlight.repositories.SupplierOrderDetailsRepo;
import com.erp.greenlight.repositories.SupplierOrderRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierOrderService {
    @Autowired
    SupplierOrderRepo supplierOrderRepo;

    @Autowired
    SupplierOrderDetailsRepo supplierOrderDetailsRepo;
     SupplierOrderMapper mapper;
    public List<SupplierOrder> getAllSupplierOrders(){
        return supplierOrderRepo.findAll();
    }
    public Optional<SupplierOrder> getSupplierOrderById( Long id){
        return Optional.of(supplierOrderRepo.findById(id).get());
    }
    public SupplierOrder saveSupplierOrder(SupplierOrderDTO supplierOrderDTO){
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setDocNo(supplierOrderDTO.getDocNo());
        supplierOrder.setSupplier(new Supplier(supplierOrderDTO.getSupplier()));
        supplierOrder.setStore(new Store(supplierOrderDTO.getStore()));
        supplierOrder.setNotes(supplierOrderDTO.getNotes());
        supplierOrder.setPillType(supplierOrderDTO.getPillType());
        supplierOrder.setOrderDate(supplierOrder.getOrderDate());
        supplierOrder.setTotalCost(BigDecimal.ZERO);
        return supplierOrderRepo.save(supplierOrder);
    }

    public SupplierOrder updateSupplierOrder(SupplierOrder supplierOrder){
//        SupplierOrder supplierOrder = supplierOrderRepo.findById(supplierOrderDTO.getOrderId()).get();
//        supplierOrder.setDocNo(supplierOrderDTO.getDocNo());
//        supplierOrder.setSupplier(new Supplier(supplierOrderDTO.getSupplier()));
//        supplierOrder.setStore(new Store(supplierOrderDTO.getStore()));
//        supplierOrder.setNotes(supplierOrderDTO.getNotes());
//        supplierOrder.setPillType(supplierOrderDTO.getPillType());
        return supplierOrderRepo.save(supplierOrder);
    }
    @Transactional
    public boolean deleteSupplierOrder(Long id){
         for(SupplierOrderDetails supplierOrderDetails: supplierOrderRepo.findById(id).get().getSupplierOrderDetailsItems()){
             supplierOrderDetailsRepo.deleteById(supplierOrderDetails.getId());
         }
         supplierOrderRepo.deleteById(id);
        return true;

    }

    @Transactional
    public boolean checkOrderIsApproved(Long id){
        SupplierOrder supplierOrder=supplierOrderRepo.findById(id).orElseThrow();
       return supplierOrder.getIsApproved();
    }


    public String approveSupplierOrder(SupplierOrder supplierOrder) {
        //count order items first to see if it contains items or not?
       //List<SupplierOrderDetails> orderItems= supplierOrderDetailsRepo.findByOrderId(id);
        if(supplierOrderDetailsRepo.findByOrderId(supplierOrder.getId()).size()==0){
            return "عفوا لايمكن اعتماد الفاتورة قبل اضافة خدمات عليها";
        }
        return "تمت بنجاح";
    }
}

