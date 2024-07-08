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
            data.put("purchaseCounter",  supplierOrderRepo.getSupplierWithOrderCountBySupplierId(supplierId));
            data.put("purchaseReturnCounter",  supplierOrderRepo.getSupplierWithOrderReturnCountBySupplierId(supplierId));

            if(supplierOrderRepo.getNetForSupplierOrder(supplier.getAccount()) != null){
                data.put("purchaseTotalMoney", supplierOrderRepo.getNetForSupplierOrder(supplier.getAccount()).multiply(new BigDecimal(-1)));
            }else{
                data.put("purchaseTotalMoney", 0);
            }

            data.put("supplierOrders", supplierOrderRepo.findAllSupplierOrderBySupplier(supplier));
            data.put("supplierOrdersReturn", supplierOrderRepo.findAllSupplierOrderReturnBySupplier(supplier));
            data.put("purchaseReturnTotalMoney", supplierOrderRepo.getNetForSupplierOrderReturn(supplier.getAccount()));
            data.put("transactionsExchange", treasuriesTransactionsRepo.getExchangeForAccount(supplier.getAccount()));
            data.put("transactionsCollect", treasuriesTransactionsRepo.getCollectForAccount(supplier.getAccount()));
            return AppResponse.generateResponse("all_data", HttpStatus.OK, data, true);
            //تفصيلي
        } else {
            data.put("fromDate", fromDate);
            data.put("toDate", toDate);
            data.put("finalBalance", supplier.getAccount().getCurrentBalance());
            data.put("purchaseCounter",  supplierOrderRepo.getSupplierWithOrderCountBySupplierIdOnPeriod(supplierId, fromDate, toDate));
            data.put("purchaseReturnCounter",  supplierOrderRepo.getSupplierWithOrderReturnCountBySupplierIdOnPeriod(supplierId, fromDate, toDate));

            if(supplierOrderRepo.getSumOfMoneyByAccountOnPeriod(supplier.getAccount(), fromDate, toDate) != null){
                data.put("purchaseTotalMoney", supplierOrderRepo.getSumOfMoneyByAccountOnPeriod(supplier.getAccount(), fromDate, toDate).multiply(new BigDecimal(-1)));
            }else{
                data.put("purchaseTotalMoney", 0);

            }
            data.put("supplierOrders", supplierOrderRepo.findAllSupplierOrderBySupplierOnPeriod(supplier, fromDate, toDate));
            data.put("supplierOrdersReturn", supplierOrderRepo.findAllSupplierOrderReturnBySupplierOnPeriod(supplier, fromDate, toDate));
            data.put("purchaseReturnTotalMoney", supplierOrderRepo.getReturnsSumOfMoneyByAccountOnPeriod(supplier.getAccount(), fromDate, toDate));
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
            data.put("salesCounter",  salesInvoiceRepo.getSalesCountByCustomerId(customerId));
            data.put("salesReturnCounter",  salesInvoiceReturnRepo.getSalesReturnCountByCustomerId(customerId));

            if(salesInvoiceRepo.getSumOfMoneyByAccount(customer.getAccount()) != null){
                data.put("salesTotalMoney", salesInvoiceRepo.getSumOfMoneyByAccount(customer.getAccount()).multiply(new BigDecimal(-1)));
            }else{
                data.put("salesTotalMoney", 0);

            }
            data.put("salesReturnTotalMoney", salesInvoiceReturnRepo.getSumOfMoneyByAccount(customer.getAccount()));

            data.put("salesInvoices", salesInvoiceRepo.findAllByCustomer(customer));
            data.put("salesReturnInvoices", salesInvoiceReturnRepo.findAllByCustomer(customer));
            data.put("transactionsExchange", treasuriesTransactionsRepo.getExchangeForAccount(customer.getAccount()));
            data.put("transactionsCollect", treasuriesTransactionsRepo.getCollectForAccount(customer.getAccount()));
            return AppResponse.generateResponse("all_data", HttpStatus.OK, data, true);
            //تفصيلي
        } else {
            data.put("fromDate", fromDate);
            data.put("toDate", toDate);
            data.put("finalBalance", customer.getAccount().getCurrentBalance());
            data.put("salesCounter",  salesInvoiceRepo.getSalesCountByCustomerIdOnPeriod(customerId, fromDate, toDate));
            data.put("salesReturnCounter",  salesInvoiceReturnRepo.getSalesReturnCountByCustomerIdOnPeriod(customerId, fromDate, toDate));

            if(salesInvoiceRepo.getSumOfMoneyByAccountOnPeriod(customer.getAccount(), fromDate, toDate) != null){
                data.put("salesTotalMoney", salesInvoiceRepo.getSumOfMoneyByAccountOnPeriod(customer.getAccount(), fromDate, toDate).multiply(new BigDecimal(-1)));
            }else{
                data.put("salesTotalMoney", 0);
            }
            data.put("salesReturnTotalMoney", salesInvoiceReturnRepo.getSumOfMoneyByAccountOnPeriod(customer.getAccount(), fromDate, toDate));

            data.put("salesInvoices", salesInvoiceRepo.findAllByCustomerOnPeriod(customer, fromDate, toDate));
            data.put("salesReturnInvoices", salesInvoiceReturnRepo.findAllByCustomerOnPeriod(customer, fromDate, toDate));

            data.put("transactionsExchange", treasuriesTransactionsRepo.getExchangeForAccountOnPeriod(customer.getAccount(), fromDate, toDate));
            data.put("transactionsCollect", treasuriesTransactionsRepo.getCollectForAccountOnPeriod(customer.getAccount(), fromDate, toDate));

        }

        return AppResponse.generateResponse("all_data", HttpStatus.OK, null, true);
    }

}
