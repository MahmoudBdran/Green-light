package com.erp.greenlight.repositories;

import com.erp.greenlight.models.SarfPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SarfPermissionRepo extends JpaRepository<SarfPermission,Long> {


}
