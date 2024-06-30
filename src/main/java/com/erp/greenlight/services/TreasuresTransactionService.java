package com.erp.greenlight.services;

import com.erp.greenlight.models.InvUom;
import com.erp.greenlight.models.TreasuryTransaction;
import com.erp.greenlight.repositories.InvUomRepo;
import com.erp.greenlight.repositories.TreasuriesTransactionsRepo;
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

    public List<TreasuryTransaction> collectFindAll(){
        return treasuriesTransactionsRepo.findByMoneyGreaterThan(BigDecimal.ZERO);
    }

    public List<TreasuryTransaction> exChangeFindAll(){
        return treasuriesTransactionsRepo.findByMoneyLessThan(BigDecimal.ZERO);
    }
}
