package com.erp.greenlight.greenlightWorker.respository;

import com.erp.greenlight.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Optional<Admin> findByUsername(String username);
    Optional<Admin> findUserByEmail(String email);

}
