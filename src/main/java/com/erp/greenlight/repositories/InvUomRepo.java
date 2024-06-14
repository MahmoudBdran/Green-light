package com.erp.greenlight.repositories;

import com.erp.greenlight.models.InvUom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvUomRepo extends JpaRepository<InvUom,Long> {
    List<InvUom> findByMaster(boolean isMaster);
}
