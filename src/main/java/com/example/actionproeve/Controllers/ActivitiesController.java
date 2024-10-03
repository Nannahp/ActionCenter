package com.example.actionproeve.Controllers;

import com.example.actionproeve.Models.Activities;
import com.example.actionproeve.Services.ActivitiesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Rest - retunerer json-data.
@RestController
// Siger at denne tillader min vue ap adgang til denne backend, men min back-end
// kører jo på 8080.
@CrossOrigin(origins = "http://localhost:5173")

public class ActivitiesController {
    @Autowired
    private ActivitiesService activitiesService;

    @GetMapping("/activity")
    public Activities getActivity() {
        System.out.println("sucess");
        return new Activities();
    }

    @PostMapping("/add-activity")
    public ResponseEntity<?> saveActivity(@RequestBody Activities activity) {
        System.out.println(activity.getName());
        System.out.println(activity.getDescription());
        System.out.println("Received activity: " + activity);
        activitiesService.saveActivity(activity);

        return ResponseEntity.ok().build();

    }
}
