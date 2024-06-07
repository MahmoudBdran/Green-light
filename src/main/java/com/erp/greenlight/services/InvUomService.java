package com.erp.greenlight.services;

import com.erp.greenlight.models.Customer;
import com.erp.greenlight.models.InvUom;
import com.erp.greenlight.repositories.CustomerRepo;
import com.erp.greenlight.repositories.InvUomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class InvUomService {
    @Autowired
    InvUomRepo repo;

    public List<InvUom> getAllInvUoms(){
        return repo.findAll();
    }

    public List<InvUom> invUomsParent(){
        return repo.findByIsMaster(true);
    }

    public List<InvUom> invUomsChilds(){
        return repo.findByIsMaster(false);
    }

    public Optional<InvUom> getInvUomById(@PathVariable Long id){
        return Optional.of(repo.findById(id).get());
    }



    public InvUom saveInvUom(InvUom invUom){
        return repo.save(invUom);
    }
    public void deleteInvUom( Long id){
        repo.deleteById(id);
    }
}
