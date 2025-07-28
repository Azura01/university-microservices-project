package com.university.controller;

import com.university.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "*")
public class StockController {
    
    @Autowired
    private StockService stockService;
    
    @GetMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> getProductStock(@PathVariable Long productId) {
        return ResponseEntity.ok(stockService.getStockInfo(productId));
    }
}