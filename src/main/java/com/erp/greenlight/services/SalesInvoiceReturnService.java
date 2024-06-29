package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.mappers.SupplierOrderMapper;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SalesInvoiceReturnService {
    @Autowired
    SalesInvoiceReturnRepo salesInvoiceReturnRepo;

    @Autowired
    SalesInvoiceReturnDetailsRepo salesInvoiceReturnDetailsRepo;
    SupplierOrderMapper mapper;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private CustomerRepo customerRepo;

    public List<SalesInvoiceReturn> getAllSalesInvoicesReturn(){
        return salesInvoiceReturnRepo.findAll();
    }
    public Optional<SalesInvoiceReturn> getSalesInvoiceReturnById( Long id){
        return Optional.of(salesInvoiceReturnRepo.findById(id).get());
    }
    public SalesInvoiceReturn saveSalesInvoiceReturn(SalesInvoiceDTO salesInvoiceDTO){
//        SalesInvoice salesInvoiceReturn = new SalesInvoice();
//        if(salesInvoiceDTO.getCustomer() != null){
//            salesInvoiceReturn.setIsHasCustomer(Boolean.TRUE);
//        }else{
//            salesInvoiceReturn.setIsHasCustomer(Boolean.FALSE);
//        }
//        salesInvoiceReturn.setCustomer(new Customer(salesInvoiceDTO.getCustomer()));
//
//        salesInvoiceReturn.setIsApproved(Boolean.FALSE);
//        salesInvoiceReturn.setNotes(salesInvoiceDTO.getNotes());
//        salesInvoiceReturn.setDiscountPercent(BigDecimal.ZERO);
//        salesInvoiceReturn.setDiscountValue(BigDecimal.ZERO);
//        salesInvoiceReturn.setTaxPercent(BigDecimal.ZERO);
//        salesInvoiceReturn.setTaxValue(BigDecimal.ZERO);
//
//        salesInvoiceReturn.setTotalCostItems(BigDecimal.ZERO);
//        salesInvoiceReturn.setTotalBeforeDiscount(BigDecimal.ZERO);
//        salesInvoiceReturn.setTotalCost(BigDecimal.ZERO);
//        salesInvoiceReturn.setMoneyForAccount(BigDecimal.ZERO);
//        salesInvoiceReturn.setPillType(salesInvoiceDTO.getPillType());
//
//        salesInvoiceReturn.setWhatPaid(BigDecimal.ZERO);
//        salesInvoiceReturn.setWhatRemain(BigDecimal.ZERO);
//
//        salesInvoiceReturn.setInvoiceDate(salesInvoiceReturn.getInvoiceDate());
//        salesInvoiceReturn.setTotalCost(BigDecimal.ZERO);
//
//        return salesInvoiceRepo.save(salesInvoiceReturn);




        SalesInvoiceReturn salesInvoiceReturn = new SalesInvoiceReturn();
//        salesInvoiceReturn.setDocNo(salesInvoiceDTO.getDocNo());

                if(salesInvoiceDTO.getCustomer() != null){
            salesInvoiceReturn.setIsHasCustomer(Boolean.TRUE);
        }else{
            salesInvoiceReturn.setIsHasCustomer(Boolean.FALSE);
        }
        salesInvoiceReturn.setCustomer(new Customer(salesInvoiceDTO.getCustomer()));
                Customer customer = customerRepo.findById(salesInvoiceDTO.getCustomer()).orElseThrow();
        salesInvoiceReturn.setNotes(salesInvoiceDTO.getNotes());
        salesInvoiceReturn.setPillType(salesInvoiceDTO.getPillType());
        salesInvoiceReturn.setInvoiceDate(salesInvoiceReturn.getInvoiceDate());
        salesInvoiceReturn.setIsApproved(Boolean.FALSE);
        salesInvoiceReturn.setNotes(salesInvoiceDTO.getNotes());
        salesInvoiceReturn.setDiscountPercent(BigDecimal.ZERO);
        salesInvoiceReturn.setDiscountValue(BigDecimal.ZERO);
        salesInvoiceReturn.setTaxPercent(BigDecimal.ZERO);
        salesInvoiceReturn.setTaxValue(BigDecimal.ZERO);
        salesInvoiceReturn.setTotalCostItems(BigDecimal.ZERO);
        salesInvoiceReturn.setTotalBeforeDiscount(BigDecimal.ZERO);
        salesInvoiceReturn.setTotalCost(BigDecimal.ZERO);
        salesInvoiceReturn.setMoneyForAccount(BigDecimal.ZERO);
        salesInvoiceReturn.setPillType(salesInvoiceDTO.getPillType());
        salesInvoiceReturn.setWhatPaid(BigDecimal.ZERO);
        salesInvoiceReturn.setWhatRemain(BigDecimal.ZERO);
        salesInvoiceReturn.setTotalCost(BigDecimal.ZERO);
        salesInvoiceReturn.setAccountNumber(customer.getAccount());
        return salesInvoiceReturnRepo.save(salesInvoiceReturn);
    }

    public SalesInvoiceReturn updateSalesInvoiceReturn(SalesInvoiceReturn salesInvoiceReturn){
        return salesInvoiceReturnRepo.save(salesInvoiceReturn);
    }
    @Transactional
    public boolean deleteSalesInvoiceReturn(Long id){
         for(SalesInvoicesReturnDetails salesInvoiceDetail: salesInvoiceReturnRepo.findById(id).get().getSalesInvoicesReturnDetails()){
             salesInvoiceReturnDetailsRepo.deleteById(salesInvoiceDetail.getId());
         }
         salesInvoiceReturnRepo.deleteById(id);
        return true;

    }

    @Transactional
    public boolean checkOrderIsApproved(Long id){
        SalesInvoiceReturn salesInvoiceReturn= salesInvoiceReturnRepo.findById(id).orElseThrow();
       return salesInvoiceReturn.getIsApproved();
    }


    public String approveSalesInvoiceReturn(SalesInvoiceReturn salesInvoiceReturn) {
        //count order items first to see if it contains items or not?
       //List<SupplierOrderDetails> orderItems= supplierOrderDetailsRepo.findByOrderId(id);
        if(salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnId(salesInvoiceReturn.getId()).isEmpty()){
            return "عفوا لايمكن اعتماد الفاتورة قبل اضافة خدمات عليها";
        }
        return "تمت بنجاح";
    }
}

