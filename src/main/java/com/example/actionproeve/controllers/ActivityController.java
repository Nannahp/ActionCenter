package com.example.actionproeve.controllers;

import com.example.actionproeve.models.Activity;
import com.example.actionproeve.services.ActivityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

// Rest - retunerer json-data.
@RestController
// Siger at denne tillader min vue ap adgang til denne backend, men min back-end
// kører jo på 8080.
@CrossOrigin(origins = "http://localhost:5173")

public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/activity")
    public Activity getActivity() {
        return new Activity();
    }

    /*

    @GetMapping("/get-activities")
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    @PostMapping("/save-activities") // New endpoint to save hardcoded activities
    public String saveActivities() {
        activityService.saveHardcodedActivities();
        return "Hardcoded activities saved!";
    }
         */
}
