package org.example.view;

import org.example.controllers.DataManagement;
import org.example.controllers.HealthDataTracker;
import org.example.models.Exercise;
import org.example.models.FoodItem;
import org.example.models.SleepRecord;
import org.example.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HealthTrackerApp  {

    private static User currentUser;
    public static HealthDataTracker healthDataTracker;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Let's track your health!");
        boolean exit = false;

        while (!exit) {
            if (currentUser == null) {
                System.out.println("1) Create a new user");
                System.out.println("2) Log in");
                System.out.println("3) Exit");
                System.out.print("Enter your choice: ");

                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine(); //take in user choice

                    switch (choice) {
                        case 1:
                            createUser(scanner);
                            break;
                        case 2:
                            login(scanner);
                            break;
                        case 3:
                            exit = true;
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Not a valid input. Please enter a valid option.");
                    scanner.nextLine();
                }
            } else {
                System.out.println("Logged in as " + currentUser.getUsername());
                System.out.println("1) Add Calorie Intake");
                System.out.println("2) Add Exercise");
                System.out.println("3) Add Sleep Record");
                System.out.println("4) Calculate Daily Caloric Balance");
                System.out.println("5) Calculate Average Sleep Duration");
                System.out.println("6) Display Exercise Log");
                System.out.println("7) Generate Health Summary");
                System.out.println("8) Log out");
                System.out.print("Enter your choice: ");


                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // enter

                    switch (choice) {
                        case 1:
                            addCalorieIntake(scanner);
                            break;
                        case 2:
                            addExercise(scanner);
                            break;
                        case 3:
                            addSleepRecord(scanner);
                            break;
                        case 4:
                            healthDataTracker.calculateDailyCaloricBalance();
                            break;
                        case 5:
                            healthDataTracker.calculateAverageSleepDuration();
                            break;
                        case 6:
                            healthDataTracker.displayExerciseLog();
                            break;
                        case 7:
                            healthDataTracker.generateHealthSummary();
                            break;
                        case 8:
                            logout();
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid user input. Please enter a valid option.");
                    scanner.nextLine(); // eat the input
                }

            }
            if (currentUser != null) {
                DataManagement.saveHealthData(healthDataTracker);
            }
        }

        System.out.println("Goodbye!");
    }
    private static void createUser(Scanner scanner) {
        System.out.print("Enter a unique username: ");
        String username = scanner.nextLine();
        currentUser = new User(username);
        healthDataTracker = new HealthDataTracker(currentUser);
        System.out.println("User created successfully.");
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        currentUser = new User(username);
        healthDataTracker = new HealthDataTracker(currentUser);
        healthDataTracker = DataManagement.loadHealthData();
        System.out.println("You are now logged in.");
    }

    private static void logout() {
        currentUser = null;
        healthDataTracker = null;
        System.out.println("You are now logged out.");
        if (healthDataTracker != null) {
            DataManagement.saveHealthData(healthDataTracker);
        }
    }
    private static void addCalorieIntake(Scanner scanner) {
        Date date = healthDataTracker.getDateFromUser("Enter date (yyyy-MM-dd): ");
        System.out.print("Enter food item name: ");
        String foodName = scanner.nextLine();
        System.out.print("Enter caloric value of food item: ");
        int calories = scanner.nextInt();
        scanner.nextLine();

        FoodItem foodItem = new FoodItem(foodName, calories);
        healthDataTracker.addCalorieIntake(date, foodItem);
        DataManagement.saveHealthData(healthDataTracker);
    }

    private static void addExercise(Scanner scanner) {
        Date date = healthDataTracker.getDateFromUser("Enter date (yyyy-MM-dd): ");
        System.out.print("Enter exercise type: ");
        String exerciseType = scanner.nextLine();
        System.out.print("Enter exercise duration in minutes: ");
        int duration = scanner.nextInt();
        System.out.print("Enter amount calories burned: ");
        int caloriesBurned = scanner.nextInt();
        scanner.nextLine();

        Exercise exercise = new Exercise(exerciseType, duration);
        healthDataTracker.addExercise(date, exercise);
        DataManagement.saveHealthData(healthDataTracker);

    }
    private static void addSleepRecord(Scanner scanner) {
        Date date = healthDataTracker.getDateFromUser("Enter the date (yyyy-MM-dd): ");
        System.out.print("Enter the fall-asleep time (HH:mm): ");
        String sleepTimeStr = scanner.nextLine();
        System.out.print("Enter the wakeup time (HH:mm): ");
        String wakeupTimeStr = scanner.nextLine();

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date sleepTime = null;
        Date wakeupTime = null;

        try {
            sleepTime = timeFormat.parse(sleepTimeStr);
            wakeupTime = timeFormat.parse(wakeupTimeStr);
        } catch (ParseException e) {
            System.out.println("Invalid format. Please enter a time in the format HH:mm.");
        }

        if (sleepTime != null && wakeupTime != null) {
            try {
                SleepRecord sleepRecord = new SleepRecord(sleepTime, wakeupTime);
                healthDataTracker.addSleepRecord(date, sleepRecord);
            } catch (Exception e) {
                System.out.println("Error when adding sleep record: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid fall-asleep/wakeup time. Sleep record not added.");
        }
        DataManagement.saveHealthData(healthDataTracker);

    }
}
