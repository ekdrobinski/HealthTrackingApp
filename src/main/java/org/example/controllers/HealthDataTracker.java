package org.example.controllers;

import org.example.models.Exercise;
import org.example.models.FoodItem;
import org.example.models.SleepRecord;
import org.example.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HealthDataTracker {
    private User user;
    private Map<Date, List<FoodItem>> calorieIntakeMap;
    private Map<Date, List<Exercise>> exerciseMap;
    private Map<Date, SleepRecord> sleepRecordMap;

    public HealthDataTracker(User user) {
        this.user = user;
        calorieIntakeMap = new HashMap<>();
        exerciseMap = new HashMap<>();
        sleepRecordMap = new HashMap<>();

    }

//    public HealthDataTracker() {
//
//    }

    public void addCalorieIntake(Date date, FoodItem foodItem) {
        List<FoodItem> calorieIntakeList = calorieIntakeMap.getOrDefault(date, new ArrayList<>());
        calorieIntakeList.add(foodItem);
        calorieIntakeMap.put(date, calorieIntakeList);

        System.out.println("Calorie intake added successfully.");
    }

    public void addExercise(Date date, Exercise exercise) {
        List<Exercise> exerciseList = exerciseMap.getOrDefault(date, new ArrayList<>());
        exerciseList.add(exercise);
        exerciseMap.put(date, exerciseList);
        System.out.println("Exercise added successfully.");
    }

    public void addSleepRecord(Date date, SleepRecord sleepRecord) {
        sleepRecordMap.put(date, sleepRecord);
        System.out.println("Sleep record added successfully.");
    }

    public void calculateDailyCaloricBalance() {
        Date date = getDateFromUser("Enter the date (yyyy-MM-dd): ");
        List<FoodItem> calorieIntakeList = calorieIntakeMap.get(date);
        List<Exercise> exerciseList = exerciseMap.get(date);

        int totalCaloriesConsumed = 0;
        if (calorieIntakeList != null) {
            for (FoodItem foodItem : calorieIntakeList) {
                totalCaloriesConsumed += foodItem.getCalories();
            }
        }

        int totalCaloriesBurned = 0;
        if (exerciseList != null) {
            for (Exercise exercise : exerciseList) {
                totalCaloriesBurned += exercise.getCaloriesBurned();
            }
        }

        int dailyCaloricBalance = totalCaloriesConsumed - totalCaloriesBurned;
        System.out.println("Daily Caloric Balance for " + date + ": " + dailyCaloricBalance);
    }
    public void calculateAverageSleepDuration() {
        Date startDate = getDateFromUser("Enter the start date (yyyy-MM-dd): ");
        Date endDate = getDateFromUser("Enter the end date (yyyy-MM-dd): ");

        int totalHoursOfSleep = 0;
        int numDays = 0;
        for (Date date = startDate; date.compareTo(endDate) <= 0; date = addDays(date, 1)) {
            SleepRecord sleepRecord = sleepRecordMap.get(date);
            if (sleepRecord != null) {
                totalHoursOfSleep += sleepRecord.getHoursOfSleep();
                numDays++;
            }
        }

        if (numDays > 0) {
            int averageSleepDuration = totalHoursOfSleep / numDays;
            System.out.println("Average Sleep Duration: " + averageSleepDuration + " hours per day");
        } else {
            System.out.println("No sleep records found for the specified period.");
        }
    }
    public void displayExerciseLog() {
        Date date = getDateFromUser("Enter the date (yyyy-MM-dd): ");
        List<Exercise> exerciseList = exerciseMap.get(date);

        if (exerciseList != null) {
            for (Exercise exercise : exerciseList) {
                System.out.println("Exercise: " + exercise.getType());
                System.out.println("Duration: " + exercise.getDuration() + " minutes");
                System.out.println("Calories Burned: " + exercise.getCaloriesBurned());
                System.out.println();
            }
        } else {
            System.out.println("No exercise records found for the specified date.");
        }
    }
    public void generateHealthSummary() {
        Date startDate = getDateFromUser("Enter the start date (yyyy-MM-dd): ");
        Date endDate = getDateFromUser("Enter the end date (yyyy-MM-dd): ");

        int totalCaloriesConsumed = 0;
        int totalCaloriesBurned = 0;
        int totalHoursOfSleep = 0;
        int numDays = 0;
        Map<String, Integer> exerciseTypeCount = new HashMap<>();

        for (Date date = startDate; date.compareTo(endDate) <= 0; date = addDays(date, 1)) {
            List<FoodItem> calorieIntakeList = calorieIntakeMap.get(date);
            List<Exercise> exerciseList = exerciseMap.get(date);
            SleepRecord sleepRecord = sleepRecordMap.get(date);

            if (calorieIntakeList != null) {
                for (FoodItem foodItem : calorieIntakeList) {
                    totalCaloriesConsumed += foodItem.getCalories();
                }
            }

            if (exerciseList != null) {
                for (Exercise exercise : exerciseList) {
                    totalCaloriesBurned += exercise.getCaloriesBurned();
                    String exerciseType = exercise.getType();
                    exerciseTypeCount.put(exerciseType, exerciseTypeCount.getOrDefault(exerciseType, 0) + 1);
                }
            }

            if (sleepRecord != null) {
                totalHoursOfSleep += sleepRecord.getHoursOfSleep();
                numDays++;
            }
        }

        if (numDays > 0) {
            int averageCaloricBalance = (totalCaloriesConsumed - totalCaloriesBurned) / numDays;
            int averageSleepDuration = totalHoursOfSleep / numDays;

            System.out.println("Health Summary:");
            System.out.println("Total Calories Consumed: " + totalCaloriesConsumed);
            System.out.println("Total Calories Burned: " + totalCaloriesBurned);
            System.out.println("Average Daily Caloric Balance: " + averageCaloricBalance);
            System.out.println("Total Hours of Sleep: " + totalHoursOfSleep);
            System.out.println("Average Sleep Duration: " + averageSleepDuration + " hours per day");

            if (!exerciseTypeCount.isEmpty()) {
                String mostCommonExerciseType = Collections.max(exerciseTypeCount.entrySet(), Map.Entry.comparingByValue()).getKey();
                System.out.println("Most Common Exercise Type: " + mostCommonExerciseType);
            }
        } else {
            System.out.println("No health data found for the specified period.");
        }
    }
    public Date getDateFromUser(String message) {
        Scanner scanner = new Scanner(System.in);
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        while (date == null) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                date = dateFormat.parse(input);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter a date in the format yyyy-MM-dd.");
            }
        }

        return date;
    }

    private Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
}