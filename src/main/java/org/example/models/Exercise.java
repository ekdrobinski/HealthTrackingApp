package org.example.models;

public class Exercise {
    private String type;
    private int duration;
    private int caloriesBurned;

    public Exercise(String type, int duration) {
        this.type = type;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }

    public String getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }
    public int getCaloriesBurned() {
        return caloriesBurned;
    }
}