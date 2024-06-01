package com.erp.greenlight.services;

import com.erp.greenlight.models.InvItemcard;
import com.erp.greenlight.repositories.InvItemCardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class InvItemCardService {
    @Autowired
    InvItemCardRepo repo;

    public List<InvItemcard> getAllInvItemCards(){
        return repo.findAll();
    }

    public Optional<InvItemcard> getInvItemCardById(@PathVariable Long id){
        return Optional.of(repo.findById(id).get());
    }
    public InvItemcard saveInvItemCard(InvItemcard invItemcard){
        return repo.save(invItemcard);
    }
    public void deleteInvItemCard( Long id){
        repo.deleteById(id);
    }
}
