package com.erp.greenlight.repositories;

import com.erp.greenlight.enums.SupplierOrderType;
import com.erp.greenlight.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierOrderRepo extends JpaRepository<SupplierOrder, Long> {

    @Query("SELECT SUM(s.moneyForAccount) FROM SupplierOrder s WHERE s.account=:account")
    BigDecimal getNet(Account account);

    @Query("SELECT SUM(s.moneyForAccount) FROM SupplierOrder s WHERE s.orderType = 0 AND s.account=:account")
    BigDecimal getNetForSupplierOrder(Account account);

    @Query("SELECT SUM(s.moneyForAccount) FROM SupplierOrder s WHERE s.orderType = 1 AND s.account=:account")
    BigDecimal getNetForSupplierOrderReturn(Account account);

    Page<SupplierOrder> findAllByOrderType(SupplierOrderType orderType, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM suppliers_with_orders s WHERE s.order_type = 0 AND s.supplier_id =:supplierId ", nativeQuery = true)
    int getSupplierWithOrderCountBySupplierId(Long supplierId);

    @Query(value = "SELECT COUNT(*) FROM suppliers_with_orders s WHERE s.order_type = 1 AND s.supplier_id =:supplierId ", nativeQuery = true)
    int getSupplierWithOrderReturnCountBySupplierId(Long supplierId);


    @Query(value = "SELECT COUNT(*) FROM suppliers_with_orders s WHERE s.order_type = 0 AND s.supplier_id =:supplierId AND s.order_date <=:dateTo AND s.order_date >=:dateFrom", nativeQuery = true)
    int getSupplierWithOrderCountBySupplierIdOnPeriod(@Param("supplierId") Long supplierId, @Param("dateFrom")LocalDate dateFrom,@Param("dateTo") LocalDate dateTo);

    @Query(value = "SELECT COUNT(*) FROM suppliers_with_orders s WHERE s.order_type = 1 AND s.supplier_id =:supplierId AND s.order_date <=:dateTo AND s.order_date >=:dateFrom", nativeQuery = true)
    int getSupplierWithOrderReturnCountBySupplierIdOnPeriod(@Param("supplierId") Long supplierId, @Param("dateFrom")LocalDate dateFrom,@Param("dateTo") LocalDate dateTo);


    @Query("SELECT SUM(s.moneyForAccount) FROM SupplierOrder s WHERE s.orderType = 0 AND s.account=:account AND s.orderDate <=:dateTo AND s.orderDate >=:dateFrom")
    BigDecimal getSumOfMoneyByAccountOnPeriod( @Param("account") Account account, @Param("dateFrom")LocalDate dateFrom,@Param("dateTo") LocalDate dateTo);

    @Query("SELECT SUM(s.moneyForAccount) FROM SupplierOrder s WHERE s.orderType = 1 AND s.account=:account AND s.orderDate <=:dateTo AND s.orderDate >=:dateFrom")
    BigDecimal getReturnsSumOfMoneyByAccountOnPeriod(@Param("account") Account account, @Param("dateFrom")LocalDate dateFrom,@Param("dateTo") LocalDate dateTo);



    @Query("SELECT s FROM SupplierOrder s WHERE s.orderType = 0 AND s.supplier=:supplier")
    List<SupplierOrder> findAllSupplierOrderBySupplier(Supplier supplier);

    @Query("SELECT s FROM SupplierOrder s WHERE s.orderType = 0 AND s.supplier=:supplier AND s.orderDate <=:dateTo AND s.orderDate >=:dateFrom")
    List<SupplierOrder> findAllSupplierOrderBySupplierOnPeriod(Supplier supplier, @Param("dateFrom")LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

    @Query("SELECT s FROM SupplierOrder s WHERE s.orderType = 1 AND s.supplier=:supplier")
    List<SupplierOrder> findAllSupplierOrderReturnBySupplier(Supplier supplier);

    @Query("SELECT s FROM SupplierOrder s WHERE s.orderType = 1 AND s.supplier=:supplier AND s.orderDate <=:dateTo AND s.orderDate >=:dateFrom")
    List<SupplierOrder> findAllSupplierOrderReturnBySupplierOnPeriod(Supplier supplier, @Param("dateFrom")LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
}
