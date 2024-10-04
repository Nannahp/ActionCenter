package com.example.actionproeve.Controllers;

import com.example.actionproeve.Models.Activity;
import com.example.actionproeve.Services.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        System.out.println("sucess");
        return new Activity();
    }

    @PostMapping("/add-activity")
    public ResponseEntity<?> saveActivity(@RequestBody Activity activity) {
        System.out.println(activity.getName());
        System.out.println(activity.getDescription());
        System.out.println("Received activity: " + activity);
        activityService.saveActivity(activity);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/activities")
    public ResponseEntity<String> getActivities() throws IOException {
        ClassPathResource resource = new ClassPathResource("activities.json");
        byte[] jsonData = Files.readAllBytes(Paths.get(resource.getURI()));
        return ResponseEntity.ok(new String(jsonData));
    }
}