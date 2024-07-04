package com.erp.greenlight.repositories;

import com.erp.greenlight.models.SarfPermissionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SarfPermissionDetailsRepo extends JpaRepository<SarfPermissionDetail,Long> {


}
