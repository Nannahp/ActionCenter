package com.example.actionproeve.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.actionproeve.models.Activity;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {

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
