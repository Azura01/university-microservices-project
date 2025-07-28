package com.university.service;

import com.university.model.Product;
import com.university.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StockService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public Map<String, Object> getStockInfo(Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            Map<String, Object> stockInfo = new HashMap<>();
            stockInfo.put("product_id", product.getId());
            stockInfo.put("name", product.getName());
            stockInfo.put("quantity", product.getQuantity());
            return stockInfo;
        }
        return null;
    }
    
    public Map<String, Object> updateStock(Long productId, Integer newQuantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setQuantity(newQuantity);
            Product updatedProduct = productRepository.save(product);
            
            Map<String, Object> result = new HashMap<>();
            result.put("product_id", updatedProduct.getId());
            result.put("name", updatedProduct.getName());
            result.put("quantity", updatedProduct.getQuantity());
            return result;
        }
        return null;
    }
    
    public boolean reserveStock(Long productId, Integer quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            if (product.getQuantity() >= quantity) {
                product.setQuantity(product.getQuantity() - quantity);
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }
}