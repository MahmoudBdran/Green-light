package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.*;
import com.erp.greenlight.utils.AppResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SalesInvoiceService {
    @Autowired
    SalesInvoiceRepo salesInvoiceRepo;

    @Autowired
    private SalesInvoiceDetailsService salesInvoiceDetailsService;
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    TreasureRepo treasureRepo;

    @Autowired
    TreasuriesTransactionsRepo treasuriesTransactionsRepo;

    @Autowired
    CustomerService customerService;

    @PersistenceContext
    private EntityManager entityManager;


    private Map<Integer, String> MONTHS = Map.ofEntries(
            Map.entry(1, "يناير"),
            Map.entry(2, "فبراير"),
            Map.entry(3, "مارس"),
            Map.entry(4, "ابريل"),
            Map.entry(5, "مايو"),
            Map.entry(6, "يونيو"),
            Map.entry(7, "يوليو"),
            Map.entry(8, "أغسطس "),
            Map.entry(9, "سبتمبر"),
            Map.entry(10, "أكتوبر"),
            Map.entry(11, "نوفمبر"),
            Map.entry(12, "ديسمبر ")
    );

    public Page<SalesInvoice> getAllSalesInvoices(int pageIndex, int pageSize) {
            Pageable page = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return salesInvoiceRepo.findAll(page);
    }

    public Optional<SalesInvoice> getSalesInvoiceById(Long id) {
        return Optional.of(salesInvoiceRepo.findById(id).get());
    }

    public SalesInvoice saveSalesInvoice(SalesInvoiceDTO salesInvoiceDTO) {

        SalesInvoice salesInvoice = new SalesInvoice();

        salesInvoice.setIsHasCustomer(salesInvoiceDTO.getIsHasCustomer());

        if(salesInvoice.getIsHasCustomer()){
            Customer customer = customerRepo.findById(salesInvoiceDTO.getCustomer()).orElseThrow();
            salesInvoice.setCustomer(new Customer(salesInvoiceDTO.getCustomer()));
            salesInvoice.setAccount(customer.getAccount());
        }else{
            salesInvoice.setAccount(null);
            salesInvoice.setCustomer(null);
        }


        salesInvoice.setNotes(salesInvoiceDTO.getNotes());
        salesInvoice.setPillType(salesInvoiceDTO.getPillType());
        salesInvoice.setInvoiceDate(salesInvoiceDTO.getDate());
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

        return salesInvoiceRepo.save(salesInvoice);
    }

    public SalesInvoice updateSalesInvoice(SalesInvoiceDTO salesInvoiceDTO) {
        SalesInvoice salesInvoice = salesInvoiceRepo.findById(salesInvoiceDTO.getId()).orElseThrow();

        salesInvoice.setIsHasCustomer(salesInvoiceDTO.getIsHasCustomer());

        if(salesInvoice.getIsHasCustomer()){
            Customer customer = customerRepo.findById(salesInvoiceDTO.getCustomer()).orElseThrow();
            salesInvoice.setCustomer(new Customer(salesInvoiceDTO.getCustomer()));
            salesInvoice.setAccount(customer.getAccount());
        }else{
            salesInvoice.setAccount(null);
            salesInvoice.setCustomer(null);
        }

        salesInvoice.setNotes(salesInvoiceDTO.getNotes());
        salesInvoice.setPillType(salesInvoiceDTO.getPillType());
        salesInvoice.setInvoiceDate(salesInvoiceDTO.getDate());


        return salesInvoiceRepo.save(salesInvoice);
    }

    @Transactional
    public boolean deleteSalesInvoice(Long id) {
        for (SalesInvoiceDetail salesInvoiceDetail : salesInvoiceRepo.findById(id).get().getSalesInvoiceDetails()) {
            salesInvoiceDetailsService.deleteItem(salesInvoiceDetail.getId());
        }
        salesInvoiceRepo.deleteById(id);
        return true;

    }

    @Transactional
    public boolean checkOrderIsApproved(Long id) {
        SalesInvoice salesInvoice = salesInvoiceRepo.findById(id).orElseThrow();
        return salesInvoice.getIsApproved();
    }

    @Transactional
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

            dataUpdateParent.setTaxValue(invoiceData.getTotalCost().multiply(request.getTaxPercent().divide(new BigDecimal(100))));

            dataUpdateParent.setTotalBeforeDiscount(invoiceData.getTotalCost());

            BigDecimal totalCost = dataUpdateParent.getTotalCost();
            BigDecimal totalCostAfterDiscount = calculateTotalAfterDiscountAndTax(totalCost, request.getDiscountType(), request.getDiscountValue(), request.getDiscountPercent(), request.getTaxPercent());




            dataUpdateParent.setTotalBeforeDiscount(dataUpdateParent.getTotalCost());
            dataUpdateParent.setTotalCost(totalCostAfterDiscount);


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
            return AppResponse.generateResponse("تم حفظ الفاتورة بنجاح", HttpStatus.OK, invoiceData, true);

        } else {
            return AppResponse.generateResponse(" الفاتوره بالفعل محفوظه", HttpStatus.OK, invoiceData, true);
        }
    }

    public List<Map<String, Object>> getSalesInvoicesByMonth() {
        List<Object[]> results = salesInvoiceRepo.findSalesInvoiceCountsByMonth();
        Map<Integer, Long> salesCountMap = new HashMap<>();

        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Long count = (Long) result[1];
            salesCountMap.put(month, count);
        }

        List<Map<String, Object>> data = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Map<String, Object> map = new HashMap<>();
            map.put("month", MONTHS.get(month));
            map.put("count", salesCountMap.getOrDefault(month, 0L));
            data.add(map);
        }
        return data;
    }


    private BigDecimal calculateTotalAfterDiscountAndTax(BigDecimal invoiceTotal, Byte discountType, BigDecimal discountValue, BigDecimal discountPercentage, BigDecimal taxPercentage) {

        BigDecimal taxValue = invoiceTotal.multiply(taxPercentage.divide(new BigDecimal(100)));

        if (discountType == 0) {
            return invoiceTotal .add(taxValue);
        } else if (discountType == 1) {
            return invoiceTotal.subtract(discountPercentage.divide(new BigDecimal(100)).multiply(invoiceTotal)).add(taxValue);
        } else if (discountType == 2) {
            return invoiceTotal.subtract(discountValue).add(taxValue);
        }else {
            return null;
        }
    }

    public List<SalesInvoice> search(
            Long id,
             Long customerId,
             LocalDate fromDate,
             LocalDate toDate
    ){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SalesInvoice> cq = cb.createQuery(SalesInvoice.class);

        Root<SalesInvoice> invoice = cq.from(SalesInvoice.class);
        List<Predicate> predicates = new ArrayList<>();

        if (id != null && id != 0) {

            List<SalesInvoice> result = new ArrayList<>();
            result.add(salesInvoiceRepo.findById(id).orElseThrow());
            return result;
        }
        if (customerId != null  && customerId != 0) {
            predicates.add(cb.equal(invoice.get("customer").get("id"), customerId));
        }

        if(fromDate != null){
            predicates.add(cb.greaterThanOrEqualTo(invoice.get("invoiceDate"), fromDate));
        }
        if(toDate != null){
            predicates.add(cb.lessThanOrEqualTo(invoice.get("invoiceDate"), toDate));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();

    }

}

