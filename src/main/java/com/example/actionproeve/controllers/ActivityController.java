package com.example.actionproeve.controllers;

import com.example.actionproeve.models.Activity;
import com.example.actionproeve.services.ActivityService;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
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
    private ActivityService activityService;

    // I stedet for autowired, så injecte igennem konstruktøren. Mere rigtigt - har
    // jeg hørt.
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/activities")
    public ResponseEntity<String> getActivities() throws IOException {
        ClassPathResource resource = new ClassPathResource("activities.json");
        byte[] jsonData = Files.readAllBytes(Paths.get(resource.getURI()));
        return ResponseEntity.ok(new String(jsonData));
    }

    @PostMapping("/add-activity")
    public ResponseEntity<?> saveActivity(@RequestBody Activity activity) {
        System.out.println(activity.getActivityName());
        System.out.println(activity.getInformation());
        System.out.println("Received activity: " + activity);
        activityService.saveActivity(activity,
                "src\\main\\resources\\static\\assets\\activities.json");

        return ResponseEntity.ok().build();

    }

    /*
     *
     * @GetMapping("/get-activities")
     * public List<Activity> getAllActivities() {
     * return activityService.getAllActivities();
     * }
     *
     * @PostMapping("/save-activities") // New endpoint to save hardcoded activities
     * public String saveActivities() {
     * activityService.saveHardcodedActivities();
     * return "Hardcoded activities saved!";
     * }
     */
}
