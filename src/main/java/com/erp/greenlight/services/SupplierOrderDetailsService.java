package com.erp.greenlight.services;

import com.erp.greenlight.models.SupplierOrder;
import com.erp.greenlight.models.SupplierOrderDetails;
import com.erp.greenlight.repositories.SupplierOrderDetailsRepo;
import com.erp.greenlight.repositories.SupplierOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierOrderDetailsService {
    @Autowired
    SupplierOrderDetailsRepo supplierOrderDetailsRepo;


    public List<SupplierOrderDetails> findByOrderId(Long id){

        return supplierOrderDetailsRepo.findByOrderId(id);
    }


}

