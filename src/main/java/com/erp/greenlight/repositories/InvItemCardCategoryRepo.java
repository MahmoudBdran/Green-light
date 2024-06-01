package com.erp.greenlight.repositories;

import com.erp.greenlight.models.InvItemcard;
import com.erp.greenlight.models.InvItemcardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvItemCardCategoryRepo extends JpaRepository<InvItemcardCategory,Long> {
}
