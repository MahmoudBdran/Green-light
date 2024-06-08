package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.AdminPanelSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminPanelSettingsRepo extends JpaRepository<AdminPanelSettings,Integer> {
 AdminPanelSettings findByCustomerParentAccountNumber(int parentAccountNumber);
}
