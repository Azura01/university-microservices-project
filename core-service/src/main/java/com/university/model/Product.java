package com.university.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @ManyToOne(fetch = FetchType.EAGER)  // ← CAMBIAR DE LAZY A EAGER
    @JoinColumn(name = "supplier_id")
    @JsonIgnoreProperties({"products"})
    private Supplier supplier;
    
    // Constructores
    public Product() {}
    
    public Product(String name, String description, Double price, Integer quantity, Supplier supplier) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.supplier = supplier;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }
}