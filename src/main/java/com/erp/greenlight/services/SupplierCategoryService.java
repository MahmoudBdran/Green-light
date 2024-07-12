package com.erp.greenlight.services;

import com.erp.greenlight.models.SupplierCategory;
import com.erp.greenlight.repositories.SupplierCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierCategoryService {
    @Autowired
    private SupplierCategoryRepo supplierCategoryRepo;
    public List<SupplierCategory> findAll() {
        return supplierCategoryRepo.findAll();
    }
    public SupplierCategory findById(Integer id) {
        return supplierCategoryRepo.findById(id).get();
    }
    public SupplierCategory save(SupplierCategory supplierCategory) {
        return supplierCategoryRepo.save(supplierCategory);
    }
    public Integer deleteById(Integer id) {
        supplierCategoryRepo.deleteById(id);
        return id;
    }
    public SupplierCategory update(SupplierCategory supplierCategory) {
        return supplierCategoryRepo.save(supplierCategory);
    }


}
