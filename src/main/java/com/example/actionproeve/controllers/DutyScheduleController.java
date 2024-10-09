package com.example.actionproeve.controllers;

import com.example.actionproeve.models.DutyScheduleDTO;
import com.example.actionproeve.services.DutyScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/duty-schedules")
@CrossOrigin(origins = "http://localhost:5173")
public class DutyScheduleController {

    @Autowired
    DutyScheduleService dutyScheduleService;

    @GetMapping
    public List<DutyScheduleDTO> getAllDutySchedules() {
        return dutyScheduleService.getAllDutySchedules();
    }

    @GetMapping("/day")
    public ResponseEntity<List<DutyScheduleDTO>> getDutySchedulesByDate(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date); // Convert incoming string to LocalDate
        List<DutyScheduleDTO> duties = dutyScheduleService.getDutySchedulesByDate(localDate);
        return ResponseEntity.ok(duties);
    }
}
