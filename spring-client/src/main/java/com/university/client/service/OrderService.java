package com.university.client.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class OrderService {
    
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api";
    
    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public List<PurchaseOrder> getAllOrders() {
        ResponseEntity<List<PurchaseOrder>> response = restTemplate.exchange(
            baseUrl + "/orders", HttpMethod.GET, null, 
            new ParameterizedTypeReference<List<PurchaseOrder>>() {});
        return response.getBody();
    }
    
    public PurchaseOrder getOrderById(Long id) {
        ResponseEntity<PurchaseOrder> response = restTemplate.getForEntity(
            baseUrl + "/orders/" + id, PurchaseOrder.class);
        return response.getBody();
    }
    
    public PurchaseOrder createOrder(PurchaseOrder order) {
        ResponseEntity<PurchaseOrder> response = restTemplate.postForEntity(
            baseUrl + "/orders", order, PurchaseOrder.class);
        return response.getBody();
    }
    
    public PurchaseOrder updateOrderStatus(Long id, String status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(status, headers);
        
        ResponseEntity<PurchaseOrder> response = restTemplate.exchange(
            baseUrl + "/orders/" + id + "/status", HttpMethod.PUT, entity, PurchaseOrder.class);
        return response.getBody();
    }
    
    // Clase interna PurchaseOrder para el cliente
    public static class PurchaseOrder {
        private Long id;
        private Long customerId;
        private Long employeeId;
        private List<Long> productIds;
        private Double totalAmount;
        private String status;
        private LocalDateTime createdAt;
        
        // Constructores
        public PurchaseOrder() {}
        
        public PurchaseOrder(Long customerId, Long employeeId, List<Long> productIds, Double totalAmount) {
            this.customerId = customerId;
            this.employeeId = employeeId;
            this.productIds = productIds;
            this.totalAmount = totalAmount;
            this.status = "PENDING";
            this.createdAt = LocalDateTime.now();
        }
        
        // Getters y setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        
        public Long getEmployeeId() { return employeeId; }
        public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
        
        public List<Long> getProductIds() { return productIds; }
        public void setProductIds(List<Long> productIds) { this.productIds = productIds; }
        
        public Double getTotalAmount() { return totalAmount; }
        public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }
}