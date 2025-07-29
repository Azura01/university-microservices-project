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
        Map<String, Object> stockInfo = stockService.getStockInfo(productId);
        if (stockInfo != null) {
            return ResponseEntity.ok(stockInfo);
        }
        return ResponseEntity.notFound().build();
    }
    
    // AGREGAR ESTE MÉTODO:
    @PutMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> updateProductStock(
            @PathVariable Long productId, 
            @RequestBody Map<String, Integer> request,
            @RequestHeader(value = "X-Client-Type", required = false) String clientType) {
        
        // Validar que es un cliente móvil admin
        if (!"mobile-admin".equals(clientType)) {
            return ResponseEntity.status(403).body(Map.of(
                "error", "Acceso denegado",
                "message", "Solo administradores móviles pueden actualizar stock"
            ));
        }
        
        Integer quantity = request.get("quantity");
        if (quantity == null || quantity < 0) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Cantidad inválida",
                "message", "La cantidad debe ser un número positivo"
            ));
        }
        
        Map<String, Object> result = stockService.updateStock(productId, quantity);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }
}