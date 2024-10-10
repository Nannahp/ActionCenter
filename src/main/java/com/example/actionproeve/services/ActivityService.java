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
    private static final String JSON_FILE_PATH = "src/main/resources/static/assets/activities.json";
    private final ObjectMapper objectMapper;

    public ActivityService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    //Sletter String filePath i parameteren TODO
    public void saveActivity(Activity activity) {
        System.out.println("reached service");
        try {
            //Sletter filePath i parameteren TODO
            List<Activity> activities = readActivitiesFromFile();
            activities.add(activity);
            //Sletter filePath i parameteren TODO
            //Denne havde ikke filepath før
            writeActivitiesToFile(activities);
        } catch (IOException e) {
            e.printStackTrace(); // Improved error handling
        }
    }

    public List<Activity> readActivitiesFromFile() throws IOException {
        System.out.println("reached readactitivies");

        //Sletter filePath i parameteren TODO
        // før JSON_FILE_PATH i stedet for filepath
        File file = new File(JSON_FILE_PATH);
        if (file.exists() && file.length() > 0) {
            // Use TypeReference to read the JSON file into a List<Activity>
            List<Activity> activities = objectMapper.readValue(file, new TypeReference<List<Activity>>() {
            });
            return activities != null ? activities : new ArrayList<>(); // Return the list or an empty one
        }
        return new ArrayList<>(); // Return an empty list if the file does not exist or is empty

    }

    //Sletter String filePath i parameteren TODO
    private void writeActivitiesToFile(List<Activity> activities) {
        System.out.println("reached writeActivities");
        try {
            //Sletter filePath i parameteren TODO og tilføjet JSON_FILE_PATH
            objectMapper.writeValue(new File(JSON_FILE_PATH), activities);
        } catch (IOException e) {
            e.printStackTrace(); // Improved error handling
        }
    }
}
