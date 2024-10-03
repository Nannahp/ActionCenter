package com.example.actionproeve;

import com.example.actionproeve.Models.Activities;
import com.example.actionproeve.Services.ActivitiesService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class ActivityServiceTest {
    private ActivitiesService activityService; // Remove @Autowired
    private ObjectMapper objectMapper;
    private final String JSON_FILE_PATH = "src/main/resources/activities.json";

    @BeforeEach
    void setup() {
        // Use Mockito to mock the ObjectMapper
        objectMapper = Mockito.mock(ObjectMapper.class);
        // Initialize ActivitiesService with the mocked ObjectMapper
        activityService = new ActivitiesService(objectMapper);
    }

    // need to look up why it needs this- it relates to readValue
    @SuppressWarnings("unchecked")
    @Test
    void testSaveActivity() throws IOException {
        Activities activity = new Activities();
        activity.setName("test");
        activity.setTimes(List.of(10, 20, 30));
        activity.setDescription("This is a test sample");

        when(activityService.readActivitiesFromFile()).thenReturn(new ArrayList<>());

        List<Activities> existingActivities = activityService.readActivitiesFromFile();

        // Mock the readValue method to return existing activities
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(existingActivities);
        doNothing().when(objectMapper).writeValue(any(File.class), anyList());

        activityService.saveActivity(activity);

        verify(objectMapper).writeValue(any(File.class), anyList());

    };
}
