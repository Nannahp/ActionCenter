package com.example.actionproeve.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "duty_schedule")
public class DutySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false) // Maps to start_time column
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false) // Maps to end_time column
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnoreProperties({"password", "admin", "bookings"})  // Exclude sensitive fields
    private Employee employee;


    public DutySchedule() {
    }

    public DutySchedule(LocalDate date, LocalTime startTime, LocalTime endTime, Employee employee) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee; // Use employeeId instead of Employee object
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Transient // Use this to prevent JPA from mapping this field to a database column
    public String getEmployeeName() {
        return employee != null ? employee.getName() : null;
    }

    @Transient // Use this to prevent JPA from mapping this field to a database column
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // Transient method to get the employee username
    @Transient
    public String getEmployeeUsername() {
        return employee != null ? employee.getUsername() : null;
    }

}