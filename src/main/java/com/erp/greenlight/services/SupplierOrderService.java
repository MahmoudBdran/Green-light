package com.erp.greenlight.services;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.SupplierOrder;
import com.erp.greenlight.repositories.SupplierOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierOrderService {
    @Autowired
    SupplierOrderRepo supplierOrderRepo;
    public List<SupplierOrder> getAllSupplierOrders(){
        return supplierOrderRepo.findAll();
    }



    public Optional<SupplierOrder> getSupplierOrderById( Long id){
        return Optional.of(supplierOrderRepo.findById(id).get());
    }
    public SupplierOrder saveSupplierOrder(SupplierOrder supplierOrder){
        return supplierOrderRepo.save(supplierOrder);
    }

    public void deleteSupplierOrder( Long id){
        supplierOrderRepo.deleteById(id);
    }
}

