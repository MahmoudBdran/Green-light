package com.erp.greenlight.repositories;

import com.erp.greenlight.models.InvItemcard;
import com.erp.greenlight.models.InvUom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvUomRepo extends JpaRepository<InvUom,Long> {
}
