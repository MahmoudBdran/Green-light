package com.erp.greenlight.repositories;

import com.erp.greenlight.models.Treasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreasureRepo extends JpaRepository<Treasure,Long> {


}
