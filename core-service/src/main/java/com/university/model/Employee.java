package com.university.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    
    private String department;
    private String position;
    
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)  // ← CAMBIAR DE LAZY A EAGER
    @JsonIgnore
    private List<PurchaseOrder> processedOrders;
    
    // Constructores
    public Employee() {}
    
    public Employee(String name, String email, String password, String department, String position) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
        this.position = position;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    
    public List<PurchaseOrder> getProcessedOrders() { return processedOrders; }
    public void setProcessedOrders(List<PurchaseOrder> processedOrders) { this.processedOrders = processedOrders; }
}