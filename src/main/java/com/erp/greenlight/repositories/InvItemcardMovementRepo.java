package com.erp.greenlight.repositories;

import com.erp.greenlight.models.InvItemcardMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvItemcardMovementRepo extends JpaRepository<InvItemcardMovement,Long> {


}
