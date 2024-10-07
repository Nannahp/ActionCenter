package com.example.actionproeve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.actionproeve.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}