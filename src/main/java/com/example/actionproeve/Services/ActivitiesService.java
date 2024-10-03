package com.example.actionproeve.Services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.actionproeve.Models.Activities;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ActivitiesService {

    private static final String JSON_FILE_PATH = "src/main/resources/activities.json";
    private final ObjectMapper objectMapper;

    public ActivitiesService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void saveActivity(Activities activity) {
        try {
            List<Activities> activities = readActivitiesFromFile();
            activities.add(activity);
            writeActivitiesToFile(activities); // Refactored to a separate method
        } catch (IOException e) {
            e.getStackTrace(); // Improved error handling
        }
    }

    public List<Activities> readActivitiesFromFile() throws IOException {
        File file = new File(JSON_FILE_PATH);
        if (file.exists() && file.length() > 0) { // Ensure the file exists and is non-empty
            // Use TypeReference to read the JSON file into a List<Activities>
            List<Activities> activities = objectMapper.readValue(file, new TypeReference<List<Activities>>() {
            });
            return activities != null ? activities : new ArrayList<>(); // Return the list or an empty one
        }
        return new ArrayList<>(); // Return an empty list if the file does not exist or is empty

    }

    private void writeActivitiesToFile(List<Activities> activities) {
        try {
            objectMapper.writeValue(new File(JSON_FILE_PATH), activities);
        } catch (IOException e) {
            e.getStackTrace(); // Improved error handling
        }
    }
}