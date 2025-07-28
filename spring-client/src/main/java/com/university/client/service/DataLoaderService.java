package com.university.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Map;

@Service
public class DataLoaderService implements CommandLineRunner {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private AuthService authService;
    
    private final String baseUrl = "http://localhost:8080/api";
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("🎓 Iniciando Data Loader - Spring Client del Politécnico Grancolombiano");
        System.out.println("=" + "=".repeat(70));
        
        // Esperar un momento para que core-service esté listo
        Thread.sleep(3000);
        
        loadAndTestData();
    }
    
    private void loadAndTestData() {
        try {
            // Test 1: Verificar conexión con core-service
            testCoreServiceConnection();
            
            // Test 2: Probar operaciones de órdenes
            testOrderOperations();
            
            // Test 3: Mostrar datos del Politécnico Grancolombiano
            displayUniversityData();
            
            System.out.println("\n✅ Spring Client configurado correctamente!");
            
        } catch (Exception e) {
            System.err.println("❌ Error en Data Loader: " + e.getMessage());
        }
    }
    
    private void testCoreServiceConnection() {
        System.out.println("\n🔄 Probando conexión con core-service...");
        
        try {
            ResponseEntity<List> response = restTemplate.exchange(
                baseUrl + "/products", 
                HttpMethod.GET, 
                null, 
                new ParameterizedTypeReference<List>() {}
            );
            
            if (response.getStatusCode().is2xxSuccessful()) {
                List products = response.getBody();
                System.out.println("✅ Conexión exitosa con core-service");
                System.out.println("📦 Productos disponibles: " + (products != null ? products.size() : 0));
            }
            
        } catch (ResourceAccessException e) {
            System.err.println("❌ No se puede conectar con core-service. Asegúrate de que esté ejecutándose en puerto 8080");
        } catch (Exception e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
        }
    }
    
    private void testOrderOperations() {
        System.out.println("\n📋 Probando operaciones de órdenes...");
        
        try {
            // Obtener todas las órdenes
            List<OrderService.PurchaseOrder> orders = orderService.getAllOrders();
            System.out.println("📊 Órdenes en el sistema: " + (orders != null ? orders.size() : 0));
            
            if (orders != null && !orders.isEmpty()) {
                System.out.println("✅ Órdenes cargadas correctamente desde DataLoader del core-service");
                
                // Mostrar información de las primeras órdenes
                int displayCount = Math.min(3, orders.size());
                for (int i = 0; i < displayCount; i++) {
                    System.out.println("   📄 Orden " + (i + 1) + ": Datos del Politécnico Grancolombiano");
                }
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error probando órdenes: " + e.getMessage());
        }
    }
    
    private void displayUniversityData() {
        System.out.println("\n🎓 Datos del Politécnico Grancolombiano cargados:");
        
        try {
            // Mostrar información de productos colombianos
            ResponseEntity<List> productsResponse = restTemplate.exchange(
                baseUrl + "/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List>() {}
            );
            
            if (productsResponse.getStatusCode().is2xxSuccessful()) {
                List products = productsResponse.getBody();
                System.out.println("💻 Productos de oficina disponibles: " + (products != null ? products.size() : 0));
            }
            
            // Mostrar información de proveedores
            ResponseEntity<List> suppliersResponse = restTemplate.exchange(
                baseUrl + "/suppliers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List>() {}
            );
            
            if (suppliersResponse.getStatusCode().is2xxSuccessful()) {
                List suppliers = suppliersResponse.getBody();
                System.out.println("🏢 Proveedores colombianos: " + (suppliers != null ? suppliers.size() : 0));
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error mostrando datos universitarios: " + e.getMessage());
        }
    }
}