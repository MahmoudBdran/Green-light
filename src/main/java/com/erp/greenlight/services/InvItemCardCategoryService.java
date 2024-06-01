package com.erp.greenlight.services;

import com.erp.greenlight.models.InvItemcard;
import com.erp.greenlight.models.InvItemcardCategory;
import com.erp.greenlight.repositories.InvItemCardCategoryRepo;
import com.erp.greenlight.repositories.InvItemCardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class InvItemCardCategoryService {
    @Autowired
    InvItemCardCategoryRepo repo;

    public List<InvItemcardCategory> getAllInvItemCardCategories(){
        return repo.findAll();
    }

    public Optional<InvItemcardCategory> getInvItemCardCategoryById(@PathVariable Long id){
        return Optional.of(repo.findById(id).get());
    }
    public InvItemcardCategory saveInvItemCardCategory(InvItemcardCategory invItemcardCategory){
        return repo.save(invItemcardCategory);
    }
    public void deleteInvItemCardCategory( Long id){
        repo.deleteById(id);
    }
}
