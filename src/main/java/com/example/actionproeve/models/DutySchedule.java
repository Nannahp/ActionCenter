package com.example.actionproeve.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

//Is still needed to create the table through JPA
@Entity
@Table(name = "duty_schedule")
public class DutySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnoreProperties({"username", "password", "admin", "bookings"})  // Exclude sensitive fields
    private Employee employee;

    public DutySchedule() {
    }

    public DutySchedule(LocalTime startTime, LocalTime endTime, LocalDate date, Employee employee) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    //Method to retrieve the employee name
    @Transient
    public String getEmployeeName() {
        return employee != null ? employee.getName() : null;
    }

    //Method to retrieve the employee ID
    @Transient
    public Long getEmployeeId() {
        return employee != null ? employee.getId() : null;
    }
}
