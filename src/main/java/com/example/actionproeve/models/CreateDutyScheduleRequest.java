package com.example.actionproeve.models;

import java.time.LocalDate;
import java.time.LocalTime;

//Same princip as a DTO
public class CreateDutyScheduleRequest {
    private Long employeeId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public CreateDutyScheduleRequest(Long employeeId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.employeeId = employeeId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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
}
