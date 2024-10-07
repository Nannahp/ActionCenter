package com.example.actionproeve.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.actionproeve.models.Activity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ActivityService {
    private static final String JSON_FILE_PATH = "src/main/resources/activities.json";
    private final ObjectMapper objectMapper;

    public ActivityService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void saveActivity(Activity activity) {
        try {
            List<Activity> activities = readActivitiesFromFile();
            activities.add(activity);
            writeActivitiesToFile(activities); // Refactored to a separate method
        } catch (IOException e) {
            e.getStackTrace(); // Improved error handling
        }
    }

    public List<Activity> readActivitiesFromFile() throws IOException {
        File file = new File(JSON_FILE_PATH);
        if (file.exists() && file.length() > 0) { // Ensure the file exists and is non-empty
            // Use TypeReference to read the JSON file into a List<Activities>
            List<Activity> activities = objectMapper.readValue(file, new TypeReference<List<Activity>>() {
            });
            return activities != null ? activities : new ArrayList<>(); // Return the list or an empty one
        }
        return new ArrayList<>(); // Return an empty list if the file does not exist or is empty

    }

    private void writeActivitiesToFile(List<Activity> activities) {
        try {
            objectMapper.writeValue(new File(JSON_FILE_PATH), activities);
        } catch (IOException e) {
            e.getStackTrace(); // Improved error handling
        }
    }

    /*
     * public List<Activity> getAllActivities() {
     * return activityRepository.findAll();
     * }
     * 
     * public Activity getActivityById(Long id) {
     * return activityRepository.findById(id).orElse(null);
     * }
     * 
     * public Activity saveActivity(Activity activity) {
     * return activityRepository.save(activity);
     * }
     * 
     * public void deleteActivity(Long id) {
     * activityRepository.deleteById(id);
     * }
     * 
     * // Method to save hardcoded activities
     * public void saveHardcodedActivities() {
     * List<Activity> activities = new ArrayList<>();
     * 
     * // Create hardcoded activities
     * Activity activity1 = new Activity();
     * activity1.setName("Running");
     * activity1.setTimes(List.of("07:00", "18:00")); // Example times
     * activity1.setDescription("A morning run to start the day.");
     * activity1.setImageUrl("http://example.com/running.jpg");
     * 
     * Activity activity2 = new Activity();
     * activity2.setName("Swimming");
     * activity2.setTimes(List.of("10:00", "16:00")); // Example times
     * activity2.setDescription("Swimming for relaxation and fitness.");
     * activity2.setImageUrl("http://example.com/swimming.jpg");
     * 
     * // Add activities to the list
     * activities.add(activity1);
     * activities.add(activity2);
     * 
     * // Save all activities to the database
     * activityRepository.saveAll(activities);
     * }
     */

}
