package com.example.actionproeve.controllers;

import com.example.actionproeve.models.CreateDutyScheduleRequest;
import com.example.actionproeve.models.DutySchedule;
import com.example.actionproeve.models.Employee;
import com.example.actionproeve.services.DutyScheduleService;
import com.example.actionproeve.services.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/duty-schedules")
@CrossOrigin(origins = "http://localhost:5173")
public class DutyScheduleController {

    @Autowired
    DutyScheduleService dutyScheduleService;

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/day")
    public ResponseEntity<List<DutySchedule>> getDutySchedulesByDate(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<DutySchedule> duties = dutyScheduleService.getDutySchedulesByDate(localDate);
        return ResponseEntity.ok(duties);
    }

    @PostMapping("/add-duty")
    public ResponseEntity<List<DutySchedule>> createDutySchedules(@RequestBody List<CreateDutyScheduleRequest> dutyRequests) {
        List<DutySchedule> savedSchedules = new ArrayList<>();

        for (CreateDutyScheduleRequest request : dutyRequests) {
            // Fetch the employee by ID
            Employee employee = employeeService.findById(request.getEmployeeId())
                    .orElseThrow(() -> new ResourceAccessException("Employee not found"));

            // Create a new DutySchedule object
            DutySchedule dutySchedule = new DutySchedule();
            dutySchedule.setDate(request.getDate());
            dutySchedule.setStartTime(request.getStartTime());
            dutySchedule.setEndTime(request.getEndTime());
            dutySchedule.setEmployee(employee); // Set the Employee object

            // Save the DutySchedule
            savedSchedules.add(dutyScheduleService.saveDutySchedule(dutySchedule));
        }

        return new ResponseEntity<>(savedSchedules, HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteDutySchedule(@RequestParam Long id) {
        dutyScheduleService.deleteDutySchedule(id);
        return ResponseEntity.ok().body("Duty schedule deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<DutySchedule>> getDutySchedulesForLoggedInUser() {
        List<DutySchedule> dutySchedules = dutyScheduleService.getAllDuties();
        dutySchedules.forEach(duty -> {
        });
        return ResponseEntity.ok(dutySchedules);
    }

}
