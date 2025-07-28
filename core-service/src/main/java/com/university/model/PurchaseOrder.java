package com.university.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.university.model.enums.PurchaseOrderStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime orderDate;
    
    @ManyToOne(fetch = FetchType.EAGER)  // Cambiado a EAGER
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties({"orders"})  // Cambiado de @JsonBackReference
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.EAGER)  // Cambiado a EAGER
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties({"processedOrders"})
    private Employee employee;
    
    @ManyToMany(fetch = FetchType.EAGER)  // Cambiado a EAGER
    @JoinTable(
        name = "order_products",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnoreProperties({"supplier"})
    private List<Product> products;
    
    @Enumerated(EnumType.STRING)
    private PurchaseOrderStatus status;
    
    private Double totalAmount;
    
    // Constructores
    public PurchaseOrder() {
        this.orderDate = LocalDateTime.now();
        this.status = PurchaseOrderStatus.PENDING;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
    
    public PurchaseOrderStatus getStatus() { return status; }
    public void setStatus(PurchaseOrderStatus status) { this.status = status; }
    
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
}