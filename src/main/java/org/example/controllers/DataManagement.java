package org.example.controllers;

import org.example.models.Exercise;
import org.example.models.FoodItem;
import org.example.models.SleepRecord;

import java.io.*;
import java.util.*;

public class DataManagement {
    private static final String HEALTH_DATA_FILE = "/Users/edrobi/IdeaProjects/ElizabethHealthApp7/src/main/java/org/example/health_data.txt";
    private static final String CALORIE_INTAKE_FILE = "/Users/edrobi/IdeaProjects/ElizabethHealthApp7/src/main/java/org/example/calorie_intake_data.txt";
    private static final String EXERCISE_RECORD_FILE = "/Users/edrobi/IdeaProjects/ElizabethHealthApp7/src/main/java/org/example/exercise_record_data.txt";
    private static final String SLEEP_RECORD_FILE = "/Users/edrobi/IdeaProjects/ElizabethHealthApp7/src/main/java/org/example/sleep_record_data.txt";

    public static void saveHealthData(HealthDataTracker healthData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HEALTH_DATA_FILE))) {
            writer.write(healthData.toString());
            System.out.println("Health data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving health data: " + e.getMessage());
        }
    }

    public static HealthDataTracker loadHealthData() {
        HealthDataTracker healthData = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(HEALTH_DATA_FILE))) {
            // Read the contents of the file and create HealthDataTracker object
            // Example: healthData = new HealthDataTracker(reader.readLine());
            System.out.println("Health data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading health data: " + e.getMessage());
        }
        return healthData;
    }

    public static void saveCalorieIntakeData(Map<Date, List<FoodItem>> calorieIntakeMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CALORIE_INTAKE_FILE))) {
            for (Map.Entry<Date, List<FoodItem>> entry : calorieIntakeMap.entrySet()) {
                Date date = entry.getKey();
                List<FoodItem> foodItems = entry.getValue();
                writer.write(date.toString());
                writer.newLine();
                for (FoodItem foodItem : foodItems) {
                    writer.write(foodItem.toString());
                    writer.newLine();
                }
                writer.newLine();
            }
            System.out.println("Calorie intake data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving calorie intake data: " + e.getMessage());
        }
    }

    public static void saveExerciseRecordData(Map<Date, List<Exercise>> exerciseRecordMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EXERCISE_RECORD_FILE))) {
            for (Map.Entry<Date, List<Exercise>> entry : exerciseRecordMap.entrySet()) {
                Date date = entry.getKey();
                List<Exercise> exercises = entry.getValue();
                writer.write(date.toString());
                writer.newLine();
                for (Exercise exercise : exercises) {
                    writer.write(exercise.toString());
                    writer.newLine();
                }
                writer.newLine();
            }
            System.out.println("Exercise record data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving exercise record data: " + e.getMessage());
        }
    }

    public static void saveSleepRecordData(Map<Date, SleepRecord> sleepRecordMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SLEEP_RECORD_FILE))) {
            for (Map.Entry<Date, SleepRecord> entry : sleepRecordMap.entrySet()) {
                Date date = entry.getKey();
                SleepRecord sleepRecord = entry.getValue();
                writer.write(date.toString());
                writer.newLine();
                writer.write(sleepRecord.toString());
                writer.newLine();
                writer.newLine();
            }
            System.out.println("Sleep record data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving sleep record data: " + e.getMessage());
        }
    }
}




