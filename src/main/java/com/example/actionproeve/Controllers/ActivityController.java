package com.example.actionproeve.Controllers;

import com.example.actionproeve.Models.Activities;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// Rest - retunerer json-data.
@RestController
// Siger at denne tillader min vue ap adgang til denne backend, men min back-end kører jo på 8080.
@CrossOrigin (origins = "http://localhost:5173")

public class ActivitiesController {

    @GetMapping("/activities")
    public ResponseEntity<String> getActivities() throws IOException {
        ClassPathResource resource = new ClassPathResource("activities.json");
        byte[] jsonData = Files.readAllBytes(Paths.get(resource.getURI()));
        return ResponseEntity.ok(new String(jsonData));
    }



}
