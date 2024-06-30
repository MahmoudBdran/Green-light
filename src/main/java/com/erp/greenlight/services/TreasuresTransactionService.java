package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.SaveTreasuryTransactionDto;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    public List<TreasuryTransaction> collectFindAll(){
        return treasuriesTransactionsRepo.findByMoneyGreaterThan(BigDecimal.ZERO);
    }

    public List<TreasuryTransaction> exChangeFindAll(){
        return treasuriesTransactionsRepo.findByMoneyLessThan(BigDecimal.ZERO);
    }

    public BigDecimal getAvailableBalance(){
        return treasuriesTransactionsRepo.getAvailableBalance();
    }
    
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
        dataInsert.setByan(request.getBayn());

        treasuriesTransactionsRepo.save(dataInsert);
        treasure.setLastIsalExchange(dataInsert.getId());
        traTreasureRepo.save(treasure);

        if(account.getAccountType().getId() == 2){
            Supplier supplier = supplierService.getSupplierByAccount(account.getId());
            supplierService.refreshAccountForSupplier(account, supplier);
        } else if (account.getAccountType().getId() == 3) {
            Customer customer = customerService.getCustomerByAccount(account.getId());

            customerService.refreshAccountForCustomer(account, customer);
        }

        return dataInsert;
    }
}
