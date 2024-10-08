package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.SalesInvoiceDTO;
import com.erp.greenlight.mappers.SupplierOrderMapper;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.*;
import com.erp.greenlight.utils.AppResponse;
import jakarta.persistence.EntityManager;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    CustomerService customerService;

    @Autowired
    TreasuriesTransactionsRepo treasuriesTransactionsRepo;

    @Autowired
    TreasureRepo treasureRepo;

    @Autowired
    SalesInvoiceReturnDetailsService salesInvoiceReturnDetailsService;

    @PersistenceContext
    private EntityManager entityManager;

    public Page<SalesInvoiceReturn> getAllSalesInvoicesReturn(int pageIndex, int pageSize) {
        Pageable page = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return salesInvoiceReturnRepo.findAll(page);
    }

    public Optional<SalesInvoiceReturn> getSalesInvoiceReturnById(Long id) {
        return Optional.of(salesInvoiceReturnRepo.findById(id).get());
    }

    public SalesInvoiceReturn saveSalesInvoiceReturn(SalesInvoiceDTO salesInvoiceDTO) {


        SalesInvoiceReturn salesInvoiceReturn = new SalesInvoiceReturn();
        salesInvoiceReturn.setIsHasCustomer(salesInvoiceDTO.getIsHasCustomer());

        if(salesInvoiceReturn.getIsHasCustomer()){
            Customer customer = customerRepo.findById(salesInvoiceDTO.getCustomer()).orElseThrow();
            salesInvoiceReturn.setCustomer(new Customer(salesInvoiceDTO.getCustomer()));
            salesInvoiceReturn.setAccount(customer.getAccount());
        }else{
            salesInvoiceReturn.setAccount(null);
            salesInvoiceReturn.setCustomer(null);
        }
        salesInvoiceReturn.setNotes(salesInvoiceDTO.getNotes());
        salesInvoiceReturn.setPillType(salesInvoiceDTO.getPillType());
        salesInvoiceReturn.setInvoiceDate(salesInvoiceReturn.getInvoiceDate());
        salesInvoiceReturn.setIsApproved(Boolean.FALSE);
        salesInvoiceReturn.setDiscountPercent(BigDecimal.ZERO);
        salesInvoiceReturn.setDiscountValue(BigDecimal.ZERO);
        salesInvoiceReturn.setTaxPercent(BigDecimal.ZERO);
        salesInvoiceReturn.setTaxValue(BigDecimal.ZERO);
        salesInvoiceReturn.setTotalCostItems(BigDecimal.ZERO);
        salesInvoiceReturn.setTotalBeforeDiscount(BigDecimal.ZERO);
        salesInvoiceReturn.setTotalCost(BigDecimal.ZERO);
        salesInvoiceReturn.setMoneyForAccount(BigDecimal.ZERO);
        salesInvoiceReturn.setWhatPaid(BigDecimal.ZERO);
        salesInvoiceReturn.setWhatRemain(BigDecimal.ZERO);

         return salesInvoiceReturnRepo.save(salesInvoiceReturn);
    }

    public SalesInvoiceReturn updateSalesInvoiceReturn(SalesInvoiceDTO salesInvoiceDTO) {

        SalesInvoiceReturn salesInvoiceReturn = salesInvoiceReturnRepo.findById(salesInvoiceDTO.getId()).orElseThrow();

        salesInvoiceReturn.setIsHasCustomer(salesInvoiceDTO.getIsHasCustomer());

        if(salesInvoiceReturn.getIsHasCustomer()){
            Customer customer = customerRepo.findById(salesInvoiceDTO.getCustomer()).orElseThrow();
            salesInvoiceReturn.setCustomer(new Customer(salesInvoiceDTO.getCustomer()));
            salesInvoiceReturn.setAccount(customer.getAccount());
        }else{
            salesInvoiceReturn.setAccount(null);
            salesInvoiceReturn.setCustomer(null);
        }

        salesInvoiceReturn.setNotes(salesInvoiceDTO.getNotes());
        salesInvoiceReturn.setPillType(salesInvoiceDTO.getPillType());
        salesInvoiceReturn.setInvoiceDate(salesInvoiceReturn.getInvoiceDate());


        return salesInvoiceReturnRepo.save(salesInvoiceReturn);
    }

    @Transactional
    public boolean deleteSalesInvoiceReturn(Long id) {
        for (SalesInvoicesReturnDetails salesInvoiceDetail : salesInvoiceReturnRepo.findById(id).get().getSalesInvoicesReturnDetails()) {

            salesInvoiceReturnDetailsService.deleteItem(salesInvoiceDetail.getId());
        }
        salesInvoiceReturnRepo.deleteById(id);
        return true;
    }

    @Transactional
    public boolean checkOrderIsApproved(Long id) {
        SalesInvoiceReturn salesInvoiceReturn = salesInvoiceReturnRepo.findById(id).orElseThrow();
        return salesInvoiceReturn.getIsApproved();
    }

    @Transactional
    public ResponseEntity<Object> approveSalesInvoiceReturn(SalesInvoiceReturn request) {
        SalesInvoiceReturn invoiceData = salesInvoiceReturnRepo.findById(request.getId()).orElseThrow();
        Customer customer = invoiceData.getCustomer();
        Account account = null;

        if(invoiceData.getSalesInvoicesReturnDetails().isEmpty()){
            return AppResponse.generateResponse("عفوا لايمكن اعتماد فاتورة فارغة من الاصناف !!", HttpStatus.OK, null, true);
        }

        if (!invoiceData.getIsApproved()) {

            SalesInvoiceReturn dataUpdateParent = salesInvoiceReturnRepo.findById(request.getId()).orElseThrow();

            dataUpdateParent.setMoneyForAccount(invoiceData.getTotalCost().multiply(new BigDecimal(-1)));
            dataUpdateParent.setIsApproved(Boolean.TRUE);
            dataUpdateParent.setWhatPaid(request.getWhatPaid());
            dataUpdateParent.setWhatRemain(request.getWhatRemain());

            if (invoiceData.getIsHasCustomer()) {
                dataUpdateParent.setCustomer(customer);
                dataUpdateParent.setAccount(account);
                account =  customer.getAccount();
            }

            salesInvoiceReturnRepo.save(dataUpdateParent);

            if (request.getWhatPaid().compareTo(BigDecimal.ZERO) > 0) {

                Treasure treasure = treasureRepo.findById(1L).orElseThrow();

                TreasuryTransaction dataInsertTreasuriesTransactions = new TreasuryTransaction();


                dataInsertTreasuriesTransactions.setIsalNumber(treasure.getLastIsalExchange() + 1);
                dataInsertTreasuriesTransactions.setShiftCode(1L);
                //Credit دائن
                dataInsertTreasuriesTransactions.setMoney(request.getWhatPaid().multiply(new BigDecimal(-1)));
                dataInsertTreasuriesTransactions.setTreasure(treasure);
                dataInsertTreasuriesTransactions.setMovType(new MovType(5));
                dataInsertTreasuriesTransactions.setMoveDate(LocalDate.from(LocalDateTime.now()));

                if (invoiceData.getIsHasCustomer()) {
                    dataInsertTreasuriesTransactions.setAccount(account);
                    dataInsertTreasuriesTransactions.setIsAccount(Boolean.TRUE);
                }
                dataInsertTreasuriesTransactions.setIsApproved(Boolean.TRUE);
                //debit دائن
                dataInsertTreasuriesTransactions.setMoneyForAccount(request.getWhatPaid());
                dataInsertTreasuriesTransactions.setByan("تحصيل نظير فاتورة مرتجع مبيعات  رقم" + invoiceData.getId());

                treasuriesTransactionsRepo.save(dataInsertTreasuriesTransactions);


                //update Treasuries last_isal_exhcange
                treasure.setLastIsalExchange(dataInsertTreasuriesTransactions.getId());
                treasureRepo.save(treasure);

            }
            if (invoiceData.getIsHasCustomer()) {
                customerService.refreshAccountForCustomer(account, customer);
            }

            return AppResponse.generateResponse("تم حفظ الفاتورة بنجاح", HttpStatus.OK, invoiceData, true);

        } else {
            return AppResponse.generateResponse(" الفاتوره بالفعل محفوظه", HttpStatus.OK, invoiceData, true);
        }

    }


    public List<SalesInvoiceReturn> search(
            Long id,
            Long customerId,
            LocalDate fromDate,
            LocalDate toDate
    ){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SalesInvoiceReturn> cq = cb.createQuery(SalesInvoiceReturn.class);

        Root<SalesInvoiceReturn> invoice = cq.from(SalesInvoiceReturn.class);
        List<Predicate> predicates = new ArrayList<>();

        if (id != null && id != 0) {

            List<SalesInvoiceReturn> result = new ArrayList<>();
            result.add(salesInvoiceReturnRepo.findById(id).orElseThrow());
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

