package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.SaveTreasuryTransactionDto;
import com.erp.greenlight.enums.SupplierOrderType;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.*;
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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TreasuresTransactionService {
    @Autowired
    TreasuriesTransactionsRepo  treasuriesTransactionsRepo;

    @Autowired
    TreasureRepo traTreasureRepo;

    @Autowired
    CustomerService customerService;

    @Autowired
    SupplierService supplierService;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    MovTypeRepo movTypeRepo;

    @PersistenceContext
    private EntityManager entityManager;

    public Page<TreasuryTransaction> collectFindAll(int pageIndex, int pageSize){
        Pageable page = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return treasuriesTransactionsRepo.findByMoneyGreaterThan(BigDecimal.ZERO, page);
    }

    public Page<TreasuryTransaction> exChangeFindAll(int pageIndex, int pageSize){
        Pageable page = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return treasuriesTransactionsRepo.findByMoneyLessThan(BigDecimal.ZERO, page);
    }

    public BigDecimal getAvailableBalance(){
        return treasuriesTransactionsRepo.getAvailableBalance();
    }

    @Transactional
    public TreasuryTransaction collect(SaveTreasuryTransactionDto request){
        
        TreasuryTransaction dataInsert  = new TreasuryTransaction();
        Treasure treasure = traTreasureRepo.findById(1L).orElseThrow();
        Account account = accountRepo.findById(request.getAccount()).orElseThrow();
        MovType movType = movTypeRepo.findById(request.getMovType()).orElseThrow();

        dataInsert.setIsalNumber(1L);
        dataInsert.setShiftCode(1L);
        dataInsert.setMoney(request.getMoney());
        dataInsert.setTreasure(treasure);
        dataInsert.setMovType(movType);
        dataInsert.setMoveDate(request.getMoveDate());
        dataInsert.setAccount(account);
        dataInsert.setIsAccount(Boolean.TRUE);
        dataInsert.setIsApproved(Boolean.TRUE);
        dataInsert.setMoneyForAccount(request.getMoney().multiply(new BigDecimal(-1)));
        dataInsert.setByan(request.getByan());

        treasuriesTransactionsRepo.save(dataInsert);
        treasure.setLastIsalExchange(dataInsert.getId());
        traTreasureRepo.save(treasure);

        if(account.getAccountType().getId() == 2){
            Supplier supplier = supplierService.getSupplierByAccount(account.getId());
            supplierService.refreshAccountForSupplier(account, supplier);
        } else if (account.getAccountType().getId() == 3) {
            Customer customer = customerService.getCustomerByAccount(account.getId());

            customerService.refreshAccountForCustomer(account, customer);
        }else{
            refreshAccountBalanceGeneral(account);
        }

        return dataInsert;
    }

    @Transactional
    public TreasuryTransaction exchange(SaveTreasuryTransactionDto request){

        TreasuryTransaction dataInsert  = new TreasuryTransaction();
        Treasure treasure = traTreasureRepo.findById(1L).orElseThrow();
        Account account = accountRepo.findById(request.getAccount()).orElseThrow();
        MovType movType = movTypeRepo.findById(request.getMovType()).orElseThrow();

        dataInsert.setIsalNumber(1L);
        dataInsert.setShiftCode(1L);
        dataInsert.setMoney(request.getMoney().multiply(new BigDecimal(-1)));
        dataInsert.setTreasure(treasure);
        dataInsert.setMovType(movType);
        dataInsert.setMoveDate(request.getMoveDate());
        dataInsert.setAccount(account);
        dataInsert.setIsAccount(Boolean.TRUE);
        dataInsert.setIsApproved(Boolean.TRUE);
        dataInsert.setMoneyForAccount(request.getMoney());
        dataInsert.setByan(request.getByan());

        treasuriesTransactionsRepo.save(dataInsert);
        treasure.setLastIsalExchange(dataInsert.getId());
        traTreasureRepo.save(treasure);

        if(account.getAccountType().getId() == 2){
            Supplier supplier = supplierService.getSupplierByAccount(account.getId());
            supplierService.refreshAccountForSupplier(account, supplier);
        } else if (account.getAccountType().getId() == 3) {
            Customer customer = customerService.getCustomerByAccount(account.getId());

            customerService.refreshAccountForCustomer(account, customer);
        }else{
            refreshAccountBalanceGeneral(account);
        }

        return dataInsert;
    }

    private void refreshAccountBalanceGeneral(Account accountData){

        if (accountData.getAccountType().getId() != 2 // supplier
                &&  accountData.getAccountType().getId() != 3 //customer
                && accountData.getAccountType().getId() != 4 //delegate
                && accountData.getAccountType().getId() != 5 //employee
                && accountData.getAccountType().getId() != 8) //internal department
        {
            //صافي حركة النقديه بالخزن علي حساب العميل
            BigDecimal netInTreasuriesTransactions = treasuriesTransactionsRepo.getNet(accountData);
            //صافي حركة فواتير الخدمات الخارجية والداخلية المتعلقه بالحساب المالي

            BigDecimal finalBalance = accountData.getStartBalance().add(netInTreasuriesTransactions);
            accountData.setCurrentBalance(finalBalance);

            accountRepo.save(accountData);
        }
    }

    public List<TreasuryTransaction> getLast5Transaction(){
        return treasuriesTransactionsRepo.getLast5Transaction();
    }

    public List<TreasuryTransaction> search(
            Long id,
            Long accountId,
            Integer movType,
            int type,
            LocalDate fromDate,
            LocalDate toDate
    ){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TreasuryTransaction> cq = cb.createQuery(TreasuryTransaction.class);

        Root<TreasuryTransaction> invoice = cq.from(TreasuryTransaction.class);
        List<Predicate> predicates = new ArrayList<>();

        if (id != null && id != 0) {

            List<TreasuryTransaction> result = new ArrayList<>();
            result.add(treasuriesTransactionsRepo.findById(id).orElseThrow());
            return result;
        }
        if(type==1){
            predicates.add(cb.greaterThan(invoice.get("money"), BigDecimal.ZERO));
        }else{
            predicates.add(cb.lessThan(invoice.get("money"), BigDecimal.ZERO));
        }


        if (movType != null  && movType != 0) {
            predicates.add(cb.equal(invoice.get("movType").get("id"), movType));
        }

        if (accountId != null  && accountId != 0) {
            predicates.add(cb.equal(invoice.get("account").get("id"), accountId));
        }

        if(fromDate != null){
            predicates.add(cb.greaterThanOrEqualTo(invoice.get("moveDate"), fromDate));
        }
        if(toDate != null){
            predicates.add(cb.lessThanOrEqualTo(invoice.get("moveDate"), toDate));
        }



        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();

    }

}
