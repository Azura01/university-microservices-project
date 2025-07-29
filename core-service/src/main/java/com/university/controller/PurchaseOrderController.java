package com.university.controller;

import com.university.model.PurchaseOrder;
import com.university.model.Customer;
import com.university.model.Product;
import com.university.model.enums.PurchaseOrderStatus;
import com.university.service.PurchaseOrderService;
import com.university.service.ProductService;
import com.university.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class PurchaseOrderController {
    
    @Autowired
    private PurchaseOrderService orderService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CustomerService customerService;
    
    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getOrderById(@PathVariable Long id) {
        PurchaseOrder order = orderService.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/simple")
    public ResponseEntity<?> createSimpleOrder(@RequestBody Map<String, Object> orderData) {
        try {
            // Crear customer
            String customerName = (String) orderData.get("customerName");
            String customerEmail = (String) orderData.get("customerEmail");
            
            if (customerName == null || customerEmail == null) {
                return ResponseEntity.badRequest().body("customerName and customerEmail are required");
            }
            
            // Buscar customer existente o crear uno nuevo
            Customer customer = customerService.findByEmail(customerEmail);
            if (customer == null) {
                customer = new Customer();
                customer.setName(customerName);
                customer.setEmail(customerEmail);
                customer.setAddress("");
                customer.setPhone("");
                customer = customerService.createCustomer(customer); // PERSISTIR PRIMERO
            }
            
            // Crear orden
            PurchaseOrder order = new PurchaseOrder();
            order.setCustomer(customer); // Ahora customer está persistido
            
            // Agregar producto si se especifica
            Integer productId = (Integer) orderData.get("productId");
            if (productId != null) {
                Optional<Product> productOpt = productService.getProductById(productId.longValue());
                if (productOpt.isPresent()) {
                    order.setProducts(List.of(productOpt.get()));
                } else {
                    return ResponseEntity.badRequest().body("Product with ID " + productId + " not found");
                }
            }
            
            PurchaseOrder savedOrder = orderService.createOrder(order);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating order: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<PurchaseOrder> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        try {
            PurchaseOrderStatus status = PurchaseOrderStatus.valueOf(statusUpdate.get("status"));
            PurchaseOrder updatedOrder = orderService.updateOrderStatus(id, status);
            if (updatedOrder != null) {
                return ResponseEntity.ok(updatedOrder);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PurchaseOrder>> getOrdersByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }
}