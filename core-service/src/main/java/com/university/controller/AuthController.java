package com.university.controller;

import com.university.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateEmployee(@RequestBody AuthRequest request) {
        try {
            boolean isAuthenticated = authService.authenticateEmployee(request.getEmail(), request.getPassword());
            
            if (isAuthenticated) {
                return ResponseEntity.ok(Map.of(
                    "message", "Autenticación exitosa",
                    "email", request.getEmail(),
                    "status", "authenticated",
                    "institution", "Politécnico Grancolombiano"
                ));
            } else {
                return ResponseEntity.status(401).body(Map.of(
                    "error", "Credenciales inválidas",
                    "message", "Email o contraseña incorrectos"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Error interno del servidor",
                "message", e.getMessage()
            ));
        }
    }
    
    public static class AuthRequest {
        private String email;
        private String password;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}