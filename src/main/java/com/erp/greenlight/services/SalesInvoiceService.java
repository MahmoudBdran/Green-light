package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.mappers.SupplierOrderMapper;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.*;
import com.erp.greenlight.utils.AppResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SalesInvoiceService {
    @Autowired
    SalesInvoiceRepo salesInvoiceRepo;

    @Autowired
    SalesInvoiceDetailsRepo salesInvoiceDetailsRepo;
    SupplierOrderMapper mapper;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    TreasureRepo treasureRepo;

    @Autowired
    TreasuriesTransactionsRepo treasuriesTransactionsRepo;

    @Autowired
    CustomerService customerService;

    public List<SalesInvoice> getAllSalesInvoices() {
        return salesInvoiceRepo.findAll();
    }

    public Optional<SalesInvoice> getSalesInvoiceById(Long id) {
        return Optional.of(salesInvoiceRepo.findById(id).get());
    }

    public SalesInvoice saveSalesInvoice(SalesInvoiceDTO salesInvoiceDTO) {
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

        if (salesInvoiceDTO.getCustomer() != null) {
            salesInvoice.setIsHasCustomer(Boolean.TRUE);
        } else {
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
        salesInvoice.setWhatPaid(BigDecimal.ZERO);
        salesInvoice.setWhatRemain(BigDecimal.ZERO);
        salesInvoice.setTotalCost(BigDecimal.ZERO);
        salesInvoice.setAccount(customer.getAccount());
        return salesInvoiceRepo.save(salesInvoice);
    }

    public SalesInvoice updateSalesInvoice(SalesInvoice salesInvoice) {
        return salesInvoiceRepo.save(salesInvoice);
    }

    @Transactional
    public boolean deleteSalesInvoice(Long id) {
        for (SalesInvoiceDetail salesInvoiceDetail : salesInvoiceRepo.findById(id).get().getSalesInvoiceDetails()) {
            salesInvoiceDetailsRepo.deleteById(salesInvoiceDetail.getId());
        }
        salesInvoiceRepo.deleteById(id);
        return true;

    }

    @Transactional
    public boolean checkOrderIsApproved(Long id) {
        SalesInvoice salesInvoice = salesInvoiceRepo.findById(id).orElseThrow();
        return salesInvoice.getIsApproved();
    }


    public ResponseEntity<Object> approveSalesInvoice(SalesInvoice request) {

        SalesInvoice invoiceData = salesInvoiceRepo.findById(request.getId()).orElseThrow();
        Customer customerData = invoiceData.getCustomer();
        if (!invoiceData.getIsApproved()) {

            SalesInvoice dataUpdateParent = salesInvoiceRepo.findById(request.getId()).orElseThrow();

            dataUpdateParent.setMoneyForAccount(invoiceData.getTotalCost());
            dataUpdateParent.setIsApproved(Boolean.TRUE);
            dataUpdateParent.setWhatPaid(request.getWhatPaid());
            dataUpdateParent.setWhatRemain(request.getWhatRemain());
            dataUpdateParent.setDiscountType(request.getDiscountType());
            dataUpdateParent.setDiscountPercent(request.getDiscountPercent());
            dataUpdateParent.setDiscountValue(request.getDiscountValue());
            dataUpdateParent.setTaxPercent(request.getTaxPercent());
            dataUpdateParent.setTotalBeforeDiscount(invoiceData.getTotalCost());

            BigDecimal discountValue = BigDecimal.ZERO;
            BigDecimal newTotalCost = BigDecimal.ZERO;



            if(request.getDiscountPercent().compareTo(BigDecimal.ZERO) >0){
                newTotalCost = invoiceData.getTotalCost().subtract( request.getDiscountPercent().divide(new BigDecimal(100)).multiply(invoiceData.getTotalCost()));
            }else{
                newTotalCost = invoiceData.getTotalCost().subtract( request.getDiscountValue());
            }

            dataUpdateParent.setTotalCost(newTotalCost.add(invoiceData.getTaxValue()));


            if (invoiceData.getIsHasCustomer()) {
                dataUpdateParent.setAccount(customerData.getAccount());
            }

            salesInvoiceRepo.save(dataUpdateParent);

            if (request.getWhatPaid().compareTo(BigDecimal.ZERO) > 0) {
                Treasure treasure = treasureRepo.findById(1L).orElseThrow();

                TreasuryTransaction dataInsertTreasuriesTransactions = new TreasuryTransaction();

                //Credit دائن
                dataInsertTreasuriesTransactions.setMoney(request.getWhatPaid());
                dataInsertTreasuriesTransactions.setTreasure(treasure);
                dataInsertTreasuriesTransactions.setMovType(new MovType(5));
                dataInsertTreasuriesTransactions.setMoveDate(LocalDate.from(LocalDateTime.now()));

                if (invoiceData.getIsHasCustomer()) {
                    dataInsertTreasuriesTransactions.setAccount(customerData.getAccount());
                    dataInsertTreasuriesTransactions.setIsAccount(Boolean.TRUE);
                }
                dataInsertTreasuriesTransactions.setIsApproved(Boolean.TRUE);
                //debit دائن
                dataInsertTreasuriesTransactions.setMoneyForAccount(request.getWhatPaid().multiply(new BigDecimal(-1)));
                dataInsertTreasuriesTransactions.setByan("تحصيل نظير فاتورة مبيعات  رقم" + request.getId());
                dataInsertTreasuriesTransactions.setIsalNumber(1L);
                dataInsertTreasuriesTransactions.setShiftCode(1L);

                treasuriesTransactionsRepo.save(dataInsertTreasuriesTransactions);

                treasure.setLastIsalExchange(dataInsertTreasuriesTransactions.getId());
                treasureRepo.save(treasure);
            }
            if (invoiceData.getIsHasCustomer()) {
                customerService.refreshAccountForCustomer(customerData.getAccount(), customerData);
            }
            return AppResponse.generateResponse("تم حفظ الفاتورة بنجاح", HttpStatus.OK, invoiceData , true);

        }else{
            return AppResponse.generateResponse(" الفاتوره بالفعل محفوظه", HttpStatus.OK,  null, true);
        }
    }
}

