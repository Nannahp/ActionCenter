package com.example.actionproeve.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.actionproeve.models.Activity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ActivityService {
    private final ObjectMapper objectMapper;

    public ActivityService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void saveActivity(Activity activity, String filePath) {
        System.out.println("reached service");
        try {
            List<Activity> activities = readActivitiesFromFile(filePath);
            activities.add(activity);
            writeActivitiesToFile(activities, filePath);
        } catch (IOException e) {
            e.printStackTrace(); // Improved error handling
        }
    }

    public List<Activity> readActivitiesFromFile(String filePath) throws IOException {
        System.out.println("reached readactitivies");
        File file = new File(filePath);
        if (file.exists() && file.length() > 0) {
            // Use TypeReference to read the JSON file into a List<Activity>
            List<Activity> activities = objectMapper.readValue(file, new TypeReference<List<Activity>>() {
            });
            return activities != null ? activities : new ArrayList<>(); // Return the list or an empty one
        }
        return new ArrayList<>(); // Return an empty list if the file does not exist or is empty
    }

    private void writeActivitiesToFile(List<Activity> activities, String filePath) {
        System.out.println("reached writeActivities");
        try {
            objectMapper.writeValue(new File(filePath), activities);
        } catch (IOException e) {
            e.printStackTrace(); // Improved error handling
        }
    }
}
