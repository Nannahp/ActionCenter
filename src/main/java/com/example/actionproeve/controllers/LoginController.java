package com.example.actionproeve.controllers;

import com.example.actionproeve.models.Employee;
import com.example.actionproeve.models.LoginRequest;
import com.example.actionproeve.services.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class LoginController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/auth")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        // Fetch employee from the database
        Employee employee = employeeService.findByUsername(loginRequest.getUsername());

        if (employee != null && employee.getPassword().equals(loginRequest.getPassword())) {
            // Add username to session if login is successful
            session.setAttribute("username", employee.getUsername());
            session.setAttribute("isAdmin", employee.isAdmin());
            return ResponseEntity.ok().body(Map.of(
                    "username", employee.getUsername(),
                    "isAdmin", employee.isAdmin()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @GetMapping("/check-session")
    public ResponseEntity<?> checkSession(HttpSession session) {
        if (session.getAttribute("username") != null) {
            Boolean isAdmin = (Boolean) session.getAttribute("isAdmin"); // Cast to Boolean
            return ResponseEntity.ok().body(Map.of(
                    "sessionValid", true,
                    "isAdmin", isAdmin
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session not found");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().body("Logout successful");
    }

}
