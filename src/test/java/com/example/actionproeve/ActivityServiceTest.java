package com.example.actionproeve;

import com.example.actionproeve.models.Activity;
import com.example.actionproeve.services.ActivityService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ActivityServiceTest {
    /*
     * @Mock
     * private ObjectMapper objectMapper; // Mock ObjectMapper
     * 
     * @InjectMocks
     * private ActivityService activityService; // Inject mocks into ActivityService
     * 
     * @BeforeEach
     * void setup() {
     * // No additional initialization needed as we're using @InjectMocks
     * }
     */

    private ActivityService activityService;
    private ObjectMapper objectMapper;

    @TempDir
    Path tempDir;

    private File testFile;

    @BeforeEach
    public void setUp() throws StreamWriteException, DatabindException, IOException {
        objectMapper = new ObjectMapper();
        activityService = new ActivityService(objectMapper);
        // Create a temporary JSON file to avoid altering actual files
        testFile = tempDir.resolve("testActivities.json").toFile();

        // Pre-populate the file with initial activities
        List<Activity> initialActivities = List.of(
                new Activity("test", List.of("20", "30"), "Initial Activity 1", 200, 5, 20, null));
        objectMapper.writeValue(testFile, initialActivities);
    }

    @Test
    void testReadExistingActivites() throws IOException {
        List<Activity> activities = activityService.readActivitiesFromFile(testFile.getPath());
        assertNotNull(activities);
        assertFalse(activities.isEmpty(), "should not be empty");
        Activity foundActivity = activities.stream()
                .filter(activity -> "test".equals(activity.getActivityName())).findAny().orElse(null);

        assertEquals("test", foundActivity.getActivityName());
    }

    @Test
    void testSaveActivityToFile() throws IOException {
        Activity activity = new Activity();
        activity.setActivityName("saveTest");
        activity.setDurations(List.of("10", "20", "30"));
        activity.setInformation("This is a test sample");
        activityService.saveActivity(activity, testFile.getPath());

        List<Activity> activities = activityService.readActivitiesFromFile(testFile.getPath());
        assertEquals(2, activities.size(), "there should be 2 activities now");

        // check that both old and new activity is there
        assertTrue(activities.stream().anyMatch(a -> "test".equals(a.getActivityName())));
        assertTrue(activities.stream().anyMatch(a -> "saveTest".equals((a.getActivityName()))));

    }

    /*
     * @SuppressWarnings("unchecked")
     * 
     * @Test
     * void testSaveActivity() throws IOException {
     * Activity activity = new Activity();
     * activity.setActivityName("test");
     * activity.setDurations(List.of("10", "20", "30"));
     * activity.setInformation("This is a test sample");
     * 
     * // Mock the readValue method to return an empty list
     * when(objectMapper.readValue(any(File.class), any(TypeReference.class)))
     * .thenReturn(new ArrayList<>());
     * 
     * // Mock the writeValue method to do nothing
     * doNothing().when(objectMapper).writeValue(any(File.class), anyList());
     * 
     * // Call the method under test
     * activityService.saveActivity(activity,
     * "src/main/java/com/example/restservice/resources/static/assets/activities.json"
     * );
     * 
     * // Verify that writeValue was called with any File and any List
     * verify(objectMapper).writeValue(any(File.class), anyList());
     * }
     */

}