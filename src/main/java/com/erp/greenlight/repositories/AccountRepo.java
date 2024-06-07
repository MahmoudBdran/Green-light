package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account,Long> {


    List<Account> findAllByIsParent(Boolean isParent);
}
