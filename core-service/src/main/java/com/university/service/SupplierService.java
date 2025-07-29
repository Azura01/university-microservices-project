package com.university.service;

import com.university.model.Supplier;
import com.university.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    @Transactional(readOnly = true)
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Supplier getSupplierById(Long id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        return supplier.orElse(null);
    }
    
    @Transactional
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }
    
    @Transactional
    public Supplier updateSupplier(Supplier supplier) {
        if (supplierRepository.existsById(supplier.getId())) {
            return supplierRepository.save(supplier);
        }
        return null;
    }
    
    @Transactional
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}