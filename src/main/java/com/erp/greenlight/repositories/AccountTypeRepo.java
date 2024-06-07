package com.erp.greenlight.repositories;

import com.erp.greenlight.models.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepo extends JpaRepository<AccountType,Long> {
}
