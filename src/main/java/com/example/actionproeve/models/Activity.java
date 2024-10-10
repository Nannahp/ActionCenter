package com.example.actionproeve.models;

import java.util.List;

public class Activity {

    private String activityName;
    private List<String> durations;
    private String information;
    private int price;
    private int minPeople;
    private int maxPeople;
    private String image;


    public Activity() {
    }

    public Activity(String activityName, List<String> durations, String information,
                    int price, int minPeople, int maxPeople, String image) {
        this.activityName = activityName;
        this.durations = durations;
        this.information = information;
        this.price = price;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
        this.image = image;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public List<String> getDurations() {
        return durations;
    }

    public void setDurations(List<String> durations) {
        this.durations = durations;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMinPeople() {
        return minPeople;
    }

    public void setMinPeople(int minPeople) {
        this.minPeople = minPeople;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}