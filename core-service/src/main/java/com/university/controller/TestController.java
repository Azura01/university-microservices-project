package com.university.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "message", "Core service is running",
            "timestamp", java.time.LocalDateTime.now().toString()
        ));
    }
    
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        return ResponseEntity.ok(Map.of(
            "service", "Core Service",
            "version", "1.0.0",
            "endpoints", Map.of(
                "products", "/api/products",
                "orders", "/api/orders",
                "auth", "/api/auth",
                "suppliers", "/api/suppliers",
                "stock", "/api/stock"
            )
        ));
    }
}