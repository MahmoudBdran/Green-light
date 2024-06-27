package com.erp.greenlight.services;

import com.erp.greenlight.models.Store;
import com.erp.greenlight.repositories.StoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    @Autowired
    StoreRepo repo;




    public List<Store> findAll(){
        return repo.findAll();
    }


    public Store findById(Integer id){

        return repo.findById(id).orElseThrow();
    }


    public Store save(Store store){

        return repo.save(store);
    }

}
