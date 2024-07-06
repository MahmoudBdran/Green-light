package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.TreasuryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TreasuriesTransactionsRepo extends JpaRepository<TreasuryTransaction,Long> {



    @Query("SELECT SUM(t.money) FROM TreasuryTransaction t")
    BigDecimal getBalance();

    @Query("SELECT SUM(t.moneyForAccount) FROM TreasuryTransaction t WHERE t.account=:account")
    BigDecimal getNet(Account account);

    @Query("SELECT SUM(t.money) FROM TreasuryTransaction t")
    BigDecimal getAvailableBalance();

    List<TreasuryTransaction> findByMoneyGreaterThan(BigDecimal money);
    List<TreasuryTransaction> findByMoneyLessThan(BigDecimal money);


    @Query(value = "SELECT * FROM treasuries_transactions ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<TreasuryTransaction> getLast5Transaction();


    @Query("SELECT SUM(t.moneyForAccount) FROM TreasuryTransaction t WHERE t.moneyForAccount > 0 AND t.account=:account")
    BigDecimal getExchangeForAccount(Account account);

    @Query("SELECT SUM(t.moneyForAccount) FROM TreasuryTransaction t WHERE t.moneyForAccount < 0 AND t.account=:account")
    BigDecimal getCollectForAccount(Account account);

    @Query("SELECT SUM(t.moneyForAccount) FROM TreasuryTransaction t WHERE t.moneyForAccount > 0 AND t.account=:account AND t.moveDate <=:dateTo AND t.moveDate >=:dateFrom ")
    BigDecimal getExchangeForAccountOnPeriod(@Param("account") Account account, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

    @Query("SELECT SUM(t.moneyForAccount) FROM TreasuryTransaction t WHERE t.moneyForAccount < 0 AND t.account=:account AND t.moveDate <=:dateTo AND t.moveDate >=:dateFrom")
    BigDecimal getCollectForAccountOnPeriod(@Param("account") Account account, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
}
