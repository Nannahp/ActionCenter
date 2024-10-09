package com.example.actionproeve.models;

import java.time.LocalDate;
import java.time.LocalTime;

//Needed as it gives better control to sending both employee and schedule data
//DTO for data transfer object
public class DutyScheduleDTO {
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String employeeName;  // To hold the employee's name
    private Long employeeId;      // To hold the employee's ID

    public DutyScheduleDTO() {
    }

    public DutyScheduleDTO(Long id, LocalTime startTime, LocalTime endTime, LocalDate date, String employeeName, Long employeeId) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.employeeName = employeeName;
        this.employeeId = employeeId;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
