package com.erp.greenlight.controllers;

import com.erp.greenlight.enums.ReportType;
import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.Customer;
import com.erp.greenlight.models.Supplier;
import com.erp.greenlight.repositories.*;
import com.erp.greenlight.services.AccountService;
import com.erp.greenlight.services.AccountTypeService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "http://localhost:4200")

public class ReportController {

    @Autowired
    SupplierRepo supplierRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    SupplierOrderRepo supplierOrderRepo;

    @Autowired
    TreasuriesTransactionsRepo treasuriesTransactionsRepo;

    @Autowired
    SalesInvoiceRepo salesInvoiceRepo;

    @Autowired
    SalesInvoiceReturnRepo salesInvoiceReturnRepo;



    @GetMapping("/supplierAccountReport")
    public ResponseEntity<Object> supplierAccountReport(@RequestParam(name = "supplierId") Long supplierId,
                                                        @RequestParam(name = "fromDate") LocalDate fromDate,
                                                        @RequestParam(name = "toDate") LocalDate toDate
                                                        ) {
        Map<String, Object> data = new HashMap<>();
        Supplier supplier = supplierRepo.findById(supplierId).orElseThrow();

        if( fromDate !=null && toDate != null){

            data.put("finalBalance", supplier.getAccount().getCurrentBalance());
            data.put("purchaseCounter",  supplierOrderRepo.getSupplierWithOrderOfSupplier(supplierId));
            data.put("purchaseReturnCounter",  supplierOrderRepo.getSupplierWithOrderReturnOfSupplier(supplierId));

            if(supplierOrderRepo.getNetForSupplierOrder(supplier.getAccount()) != null){
                data.put("purchaseTotalMoney", supplierOrderRepo.getNetForSupplierOrder(supplier.getAccount()).multiply(new BigDecimal(-1)));
            }else{
                data.put("purchaseTotalMoney", 0);

            }
            data.put("purchaseReturnTotalMoney", supplierOrderRepo.getNetForSupplierOrderReturn(supplier.getAccount()));
            data.put("transactionsExchange", treasuriesTransactionsRepo.getExchangeForAccount(supplier.getAccount()));
            data.put("transactionsCollect", treasuriesTransactionsRepo.getCollectForAccount(supplier.getAccount()));
            return AppResponse.generateResponse("all_data", HttpStatus.OK, data, true);
            //تفصيلي
        } else {
            data.put("fromDate", fromDate);
            data.put("toDate", toDate);
            data.put("finalBalance", supplier.getAccount().getCurrentBalance());
            data.put("purchaseCounter",  supplierOrderRepo.getSupplierWithOrderOfSupplierOnPeriod(supplierId, fromDate, toDate));
            data.put("purchaseReturnCounter",  supplierOrderRepo.getSupplierWithOrderReturnOfSupplierOnPeriod(supplierId, fromDate, toDate));

            if(supplierOrderRepo.getNetForSupplierOrderOnPeriod(supplier.getAccount(), fromDate, toDate) != null){
                data.put("purchaseTotalMoney", supplierOrderRepo.getNetForSupplierOrderOnPeriod(supplier.getAccount(), fromDate, toDate).multiply(new BigDecimal(-1)));
            }else{
                data.put("purchaseTotalMoney", 0);

            }

            data.put("purchaseReturnTotalMoney", supplierOrderRepo.getNetForSupplierOrderReturnOnPeriod(supplier.getAccount(), fromDate, toDate));
            data.put("transactionsExchange", treasuriesTransactionsRepo.getExchangeForAccountOnPeriod(supplier.getAccount(), fromDate, toDate));
            data.put("transactionsCollect", treasuriesTransactionsRepo.getCollectForAccountOnPeriod(supplier.getAccount(), fromDate, toDate));

        }

        return AppResponse.generateResponse("all_data", HttpStatus.OK, null, true);
    }

    @GetMapping("/supplierCustomerReport")
    public ResponseEntity<Object> supplierCustomerReport(@RequestParam(name = "customerId") Long customerId,
                                                        @RequestParam(name = "fromDate") LocalDate fromDate,
                                                        @RequestParam(name = "toDate") LocalDate toDate
    ) {
        Map<String, Object> data = new HashMap<>();
        Customer customer = customerRepo.findById(customerId).orElseThrow();

        if( fromDate !=null && toDate != null){

            data.put("finalBalance", customer.getAccount().getCurrentBalance());
            data.put("salesCounter",  salesInvoiceRepo.getSalesOfCustomerCount(customerId));
            data.put("salesReturnCounter",  salesInvoiceReturnRepo.getSalesReturnOfCustomerCount(customerId));

            if(salesInvoiceRepo.getNetForCustomerOrder(customer.getAccount()) != null){
                data.put("salesTotalMoney", salesInvoiceRepo.getNetForCustomerOrder(customer.getAccount()).multiply(new BigDecimal(-1)));
            }else{
                data.put("salesTotalMoney", 0);

            }
            data.put("salesReturnTotalMoney", salesInvoiceReturnRepo.getNetForSalesReturnForCustomer(customer.getAccount()));

            data.put("transactionsExchange", treasuriesTransactionsRepo.getExchangeForAccount(customer.getAccount()));
            data.put("transactionsCollect", treasuriesTransactionsRepo.getCollectForAccount(customer.getAccount()));
            return AppResponse.generateResponse("all_data", HttpStatus.OK, data, true);
            //تفصيلي
        } else {
            data.put("fromDate", fromDate);
            data.put("toDate", toDate);
            data.put("finalBalance", customer.getAccount().getCurrentBalance());
            data.put("salesCounter",  salesInvoiceRepo.getSalesOfCustomerOnPeriodCount(customerId, fromDate, toDate));
            data.put("salesReturnCounter",  salesInvoiceReturnRepo.getSalesReturnOfCustomerOnPeriodCount(customerId, fromDate, toDate));

            if(salesInvoiceRepo.getNetForSalesOnPeriod(customer.getAccount(), fromDate, toDate) != null){
                data.put("salesTotalMoney", salesInvoiceRepo.getNetForSalesOnPeriod(customer.getAccount(), fromDate, toDate).multiply(new BigDecimal(-1)));
            }else{
                data.put("salesTotalMoney", 0);
            }
            data.put("salesReturnTotalMoney", salesInvoiceReturnRepo.getNetForSalesReturnOnPeriod(customer.getAccount(), fromDate, toDate));

            data.put("transactionsExchange", treasuriesTransactionsRepo.getExchangeForAccountOnPeriod(customer.getAccount(), fromDate, toDate));
            data.put("transactionsCollect", treasuriesTransactionsRepo.getCollectForAccountOnPeriod(customer.getAccount(), fromDate, toDate));

        }

        return AppResponse.generateResponse("all_data", HttpStatus.OK, null, true);
    }

}
