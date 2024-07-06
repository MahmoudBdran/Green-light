package com.erp.greenlight.repositories;

import com.erp.greenlight.enums.SupplierOrderType;
import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.SupplierOrder;
import com.erp.greenlight.models.SupplierOrderDetails;
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

    List<SupplierOrder> findAllByOrderType(SupplierOrderType orderType);

    @Query(value = "SELECT COUNT(*) FROM suppliers_with_orders s WHERE s.order_type = 0 AND s.supplier_id =:supplierId ", nativeQuery = true)
    int getSupplierWithOrderOfSupplier(Long supplierId);

    @Query(value = "SELECT COUNT(*) FROM suppliers_with_orders s WHERE s.order_type = 1 AND s.supplier_id =:supplierId ", nativeQuery = true)
    int getSupplierWithOrderReturnOfSupplier(Long supplierId);


    @Query(value = "SELECT COUNT(*) FROM suppliers_with_orders s WHERE s.order_type = 0 AND s.supplier_id =:supplierId AND s.order_date <=:dateTo AND s.order_date >=:dateFrom", nativeQuery = true)
    int getSupplierWithOrderOfSupplierOnPeriod(@Param("supplierId") Long supplierId, @Param("dateFrom")LocalDate dateFrom,@Param("dateTo") LocalDate dateTo);

    @Query(value = "SELECT COUNT(*) FROM suppliers_with_orders s WHERE s.order_type = 1 AND s.supplier_id =:supplierId AND s.order_date <=:dateTo AND s.order_date >=:dateFrom", nativeQuery = true)
    int getSupplierWithOrderReturnOfSupplierOnPeriod(@Param("supplierId") Long supplierId, @Param("dateFrom")LocalDate dateFrom,@Param("dateTo") LocalDate dateTo);


    @Query("SELECT SUM(s.moneyForAccount) FROM SupplierOrder s WHERE s.orderType = 0 AND s.account=:account AND s.orderDate <=:dateTo AND s.orderDate >=:dateFrom")
    BigDecimal getNetForSupplierOrderOnPeriod( @Param("account") Account account, @Param("dateFrom")LocalDate dateFrom,@Param("dateTo") LocalDate dateTo);

    @Query("SELECT SUM(s.moneyForAccount) FROM SupplierOrder s WHERE s.orderType = 1 AND s.account=:account AND s.orderDate <=:dateTo AND s.orderDate >=:dateFrom")
    BigDecimal getNetForSupplierOrderReturnOnPeriod(@Param("account") Account account, @Param("dateFrom")LocalDate dateFrom,@Param("dateTo") LocalDate dateTo);
}
