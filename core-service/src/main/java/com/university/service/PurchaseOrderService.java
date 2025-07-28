package com.university.service;

import com.university.model.PurchaseOrder;
import com.university.model.enums.PurchaseOrderStatus;
import com.university.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {
    
    @Autowired
    private PurchaseOrderRepository orderRepository;
    
    public List<PurchaseOrder> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public PurchaseOrder getOrderById(Long id) {
        Optional<PurchaseOrder> order = orderRepository.findById(id);
        return order.orElse(null);
    }
    
    public PurchaseOrder createOrder(PurchaseOrder order) {
        // Calcular el total basado en los productos
        if (order.getProducts() != null && !order.getProducts().isEmpty()) {
            double total = order.getProducts().stream()
                .mapToDouble(product -> product.getPrice())
                .sum();
            order.setTotalAmount(total);
        }
        
        return orderRepository.save(order);
    }
    
    public PurchaseOrder updateOrderStatus(Long id, PurchaseOrderStatus status) {
        Optional<PurchaseOrder> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            PurchaseOrder order = orderOpt.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }
    
    public List<PurchaseOrder> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}