package com.university.client.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class AuthService {
    
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api";
    
    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public String login(String username, String password) {
        LoginRequest request = new LoginRequest(username, password);
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
            baseUrl + "/auth/login", request, AuthResponse.class);
        return response.getBody().getToken();
    }
    
    public String register(String username, String password, String email) {
        RegisterRequest request = new RegisterRequest(username, password, email);
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
            baseUrl + "/auth/register", request, AuthResponse.class);
        return response.getBody().getToken();
    }
    
    // Clases internas para requests y responses
    public static class LoginRequest {
        private String username;
        private String password;
        
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
        
        // Getters y setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    public static class RegisterRequest {
        private String username;
        private String password;
        private String email;
        
        public RegisterRequest(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }
        
        // Getters y setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
    
    public static class AuthResponse {
        private String token;
        private String message;
        
        // Getters y setters
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}