package com.example.actionproeve.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.actionproeve.models.Employee;
import com.example.actionproeve.services.EmployeeService;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee); // Save the new employee
        return ResponseEntity.ok("Employee registered successfully");
    }

    // Endpoint to fetch all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees(); // Call the service to get all employees
        return ResponseEntity.ok(employees); // Return the list of employees
    }

    // TESTING
    @PostMapping("/register/hardcoded")
    public ResponseEntity<?> registerHardcodedEmployee() {
        // Create a hardcoded employee
        Employee hardcodedEmployee = new Employee();
        hardcodedEmployee.setName("John Doe");
        hardcodedEmployee.setUsername("johndoe");
        hardcodedEmployee.setPassword("password123");
        hardcodedEmployee.setAdmin(false);

        // Save the hardcoded employee
        employeeService.saveEmployee(hardcodedEmployee);

        return ResponseEntity.ok("Hardcoded employee registered successfully");
    }
}