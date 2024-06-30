package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.TreasuryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TreasuriesTransactionsRepo extends JpaRepository<TreasuryTransaction,Long> {



    @Query("SELECT SUM(t.money) FROM TreasuryTransaction t")
    BigDecimal getBalance();

    @Query("SELECT SUM(t.moneyForAccount) FROM TreasuryTransaction t WHERE t.account=:account")
    BigDecimal getNet(Account account);


    List<TreasuryTransaction> findByMoneyGreaterThan(BigDecimal money);
    List<TreasuryTransaction> findByMoneyLessThan(BigDecimal money);
}
