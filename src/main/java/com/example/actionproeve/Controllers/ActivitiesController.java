package com.example.actionproeve.Controllers;

import com.example.actionproeve.Models.Activities;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Rest - retunerer json-data.
@RestController
// Siger at denne tillader min vue ap adgang til denne backend, men min back-end kører jo på 8080.
@CrossOrigin (origins = "http://localhost:5173")

public class ActivitiesController {

    @GetMapping("/activity")
    public Activities getActivity(){
        return new Activities();
    }
}
