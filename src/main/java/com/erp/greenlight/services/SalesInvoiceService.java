package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.mappers.SupplierOrderMapper;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.AccountRepo;
import com.erp.greenlight.repositories.CustomerRepo;
import com.erp.greenlight.repositories.SalesInvoiceRepo;
import com.erp.greenlight.repositories.SupplierOrderDetailsRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SalesInvoiceService {
    @Autowired
    SalesInvoiceRepo salesInvoiceRepo;

    @Autowired
    SupplierOrderDetailsRepo supplierOrderDetailsRepo;
     SupplierOrderMapper mapper;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private CustomerRepo customerRepo;

    public List<SalesInvoice> getAllSalesInvoices(){
        return salesInvoiceRepo.findAll();
    }
    public Optional<SalesInvoice> getSalesInvoiceById( Long id){
        return Optional.of(salesInvoiceRepo.findById(id).get());
    }
    public SalesInvoice saveSalesInvoice(SalesInvoiceDTO salesInvoiceDTO){
//        SalesInvoice salesInvoice = new SalesInvoice();
//        if(salesInvoiceDTO.getCustomer() != null){
//            salesInvoice.setIsHasCustomer(Boolean.TRUE);
//        }else{
//            salesInvoice.setIsHasCustomer(Boolean.FALSE);
//        }
//        salesInvoice.setCustomer(new Customer(salesInvoiceDTO.getCustomer()));
//
//        salesInvoice.setIsApproved(Boolean.FALSE);
//        salesInvoice.setNotes(salesInvoiceDTO.getNotes());
//        salesInvoice.setDiscountPercent(BigDecimal.ZERO);
//        salesInvoice.setDiscountValue(BigDecimal.ZERO);
//        salesInvoice.setTaxPercent(BigDecimal.ZERO);
//        salesInvoice.setTaxValue(BigDecimal.ZERO);
//
//        salesInvoice.setTotalCostItems(BigDecimal.ZERO);
//        salesInvoice.setTotalBeforeDiscount(BigDecimal.ZERO);
//        salesInvoice.setTotalCost(BigDecimal.ZERO);
//        salesInvoice.setMoneyForAccount(BigDecimal.ZERO);
//        salesInvoice.setPillType(salesInvoiceDTO.getPillType());
//
//        salesInvoice.setWhatPaid(BigDecimal.ZERO);
//        salesInvoice.setWhatRemain(BigDecimal.ZERO);
//
//        salesInvoice.setInvoiceDate(salesInvoice.getInvoiceDate());
//        salesInvoice.setTotalCost(BigDecimal.ZERO);
//
//        return salesInvoiceRepo.save(salesInvoice);
        SalesInvoice salesInvoice = new SalesInvoice();
//        salesInvoice.setDocNo(salesInvoiceDTO.getDocNo());

                if(salesInvoiceDTO.getCustomer() != null){
            salesInvoice.setIsHasCustomer(Boolean.TRUE);
        }else{
            salesInvoice.setIsHasCustomer(Boolean.FALSE);
        }
        salesInvoice.setCustomer(new Customer(salesInvoiceDTO.getCustomer()));
                Customer customer = customerRepo.findById(salesInvoiceDTO.getCustomer()).orElseThrow();
        salesInvoice.setNotes(salesInvoiceDTO.getNotes());
        salesInvoice.setPillType(salesInvoiceDTO.getPillType());
        salesInvoice.setInvoiceDate(salesInvoice.getInvoiceDate());
        salesInvoice.setIsApproved(Boolean.FALSE);
        salesInvoice.setNotes(salesInvoiceDTO.getNotes());
        salesInvoice.setDiscountPercent(BigDecimal.ZERO);
        salesInvoice.setDiscountValue(BigDecimal.ZERO);
        salesInvoice.setTaxPercent(BigDecimal.ZERO);
        salesInvoice.setTaxValue(BigDecimal.ZERO);
        salesInvoice.setTotalCostItems(BigDecimal.ZERO);
        salesInvoice.setTotalBeforeDiscount(BigDecimal.ZERO);
        salesInvoice.setTotalCost(BigDecimal.ZERO);
        salesInvoice.setMoneyForAccount(BigDecimal.ZERO);
        salesInvoice.setPillType(salesInvoiceDTO.getPillType());
        salesInvoice.setStore(new Store(salesInvoiceDTO.getStore()));
        salesInvoice.setWhatPaid(BigDecimal.ZERO);
        salesInvoice.setWhatRemain(BigDecimal.ZERO);
        salesInvoice.setTotalCost(BigDecimal.ZERO);
        salesInvoice.setAccountNumber(customer.getAccount());
        return salesInvoiceRepo.save(salesInvoice);
    }

    public SalesInvoice updateSalesInvoice(SalesInvoice salesInvoice){
        return salesInvoiceRepo.save(salesInvoice);
    }
    @Transactional
    public boolean deleteSalesInvoice(Long id){
         for(SalesInvoiceDetail salesInvoiceDetail: salesInvoiceRepo.findById(id).get().getSalesInvoiceDetails()){
             supplierOrderDetailsRepo.deleteById(salesInvoiceDetail.getId());
         }
         salesInvoiceRepo.deleteById(id);
        return true;

    }

    @Transactional
    public boolean checkOrderIsApproved(Long id){
        SalesInvoice salesInvoice= salesInvoiceRepo.findById(id).orElseThrow();
       return salesInvoice.getIsApproved();
    }


    public String approveSalesInvoice(SalesInvoice salesInvoice) {
        //count order items first to see if it contains items or not?
       //List<SupplierOrderDetails> orderItems= supplierOrderDetailsRepo.findByOrderId(id);
        if(supplierOrderDetailsRepo.findByOrderId(salesInvoice.getId()).size()==0){
            return "عفوا لايمكن اعتماد الفاتورة قبل اضافة خدمات عليها";
        }
        return "تمت بنجاح";
    }
}

