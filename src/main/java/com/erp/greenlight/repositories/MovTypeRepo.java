package com.erp.greenlight.repositories;

import com.erp.greenlight.models.MovType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovTypeRepo extends JpaRepository<MovType,Integer > {
 }
