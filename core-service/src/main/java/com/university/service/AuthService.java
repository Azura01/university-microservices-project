package com.university.service;

import com.university.model.Employee;
import com.university.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public boolean authenticateEmployee(String email, String password) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        
        if (employee.isPresent()) {
            Employee emp = employee.get();
            return emp.getPassword().equals(password);
        }
        
        return false;
    }
    
    public Optional<Employee> findEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
}