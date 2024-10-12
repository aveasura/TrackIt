package habit.controller;

import habit.model.Habit;
import habit.service.HabitAnalyticsService;
import habit.service.HabitService;
import habit.service.HabitTrackingService;
import user.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

// todo Перенести логику генерации текста аналитики в сервис, чтобы контроллер занимался только вызовом методов,
//      разделить ввод данных и бизнес логику, добавить экзепшены, создать общий метод для получения числового ввода
public class HabitController {
    private Scanner scanner;
    private HabitService habitService;
    private User loggedUser;
    private HabitTrackingService trackingService;
    private HabitAnalyticsService analyticsService;
    String choice;

    public HabitController(HabitService habitService, User loggedUser,
                           HabitTrackingService trackingService,
                           HabitAnalyticsService analyticsService
                           ) {
        this.habitService = habitService;
        this.loggedUser = loggedUser;
        this.scanner = new Scanner(System.in);
        this.trackingService = trackingService;
        this.analyticsService = analyticsService;
    }

    public void showHabitMenu() {
        System.out.println("Habit Manager\n" +
                "Choose what needs to be done:\n" + """
                        1. Create habit
                        2. Show my habits
                        3. Update habit
                        4. Track habit completion
                        5. View habit analytics
                        6. Delete habit
                        """);
        choice = scanner.nextLine();

        switch (choice) {
            case "1" -> createHabit();
            case "2" -> myHabits();
            case "3" -> updateHabit();
            case "4" -> trackHabitCompletion();
            case "5" -> viewHabitAnalytics();
            case "6" -> deleteHabit();
            default -> System.out.println("Invalid option");
        }
    }

    private void trackHabitCompletion() {
        System.out.println("Enter the habit ID you want to mark as completed:");
        myHabits();
        String habitIdInput = scanner.nextLine();

        System.out.println("Enter the date of completion (YYYY-MM-DD):");
        String dateInput = scanner.nextLine();

        try {
            LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
            trackingService.trackHabitCompletion(loggedUser.getId(), Integer.parseInt(habitIdInput), date);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    private void viewHabitAnalytics() {
        System.out.println("Enter the habit ID for which you want to view analytics:");
        myHabits();
        String habitId = scanner.nextLine();

        System.out.println("Choose the period:");
        System.out.println("1. Daily");
        System.out.println("2. Weekly");
        System.out.println("3. Monthly");

        String periodChoice = scanner.nextLine();

        String period = switch (periodChoice) {
            case "1" -> "daily";
            case "2" -> "weekly";
            case "3" -> "monthly";
            default -> null;
        };

        if (period != null) {
            String analytics = analyticsService.generateAnalytics(Integer.parseInt(habitId), period);
            System.out.println("Analytics for habit " + habitId + " over the past " + period + ":");
            System.out.println(analytics);
        } else {
            System.out.println("Invalid period selected.");
        }
    }


    private void createHabit() {
        System.out.println("Habit title");
        String title = scanner.nextLine();

        System.out.println("Habit description");
        String description = scanner.nextLine();

        System.out.println("Choose habit frequency:");
        System.out.println("1. Daily");
        System.out.println("2. Weekly");
        System.out.println("3. Monthly");
        String userChoice = scanner.nextLine();

        String frequency = switch (userChoice) {
            case "1" -> "Daily";
            case "2" -> "Weekly";
            case "3" -> "Monthly";
            default -> {
                System.out.println("Invalid choice, setting frequency to Daily by default.");
                yield "Daily";
            }
        };

        Habit habit = new Habit(title, description, frequency, loggedUser);
        habitService.create(loggedUser, habit);
        System.out.println("Habit created");
    }

    private void updateHabit() {
        System.out.print("Enter habit ID to update: ");
        myHabits();

        int habitId = Integer.parseInt(scanner.nextLine());
        Habit habitToUpdate = habitService.habitInfo(habitId);

        if (habitToUpdate == null || habitToUpdate.getUser().getId() != loggedUser.getId()) {
            System.out.println("Habit not found or does not belong to you.");
            return;
        }

        System.out.print("Enter new title for the habit: ");
        choice = scanner.nextLine();
        habitToUpdate.setName(choice);

        System.out.print("Enter new description for the habit: ");
        choice = scanner.nextLine();
        habitToUpdate.setDescription(choice);

        System.out.print("Enter new frequency for the habit: ");
        choice = scanner.nextLine();
        habitToUpdate.setFrequency(choice);

        habitService.updateHabit(loggedUser, habitToUpdate);
    }

    private void deleteHabit() {
        System.out.println("Choice id habit for delete");
        myHabits();
        int habitId = Integer.parseInt(scanner.nextLine());
        habitService.deleteHabit(loggedUser, habitId);
    }

    private void myHabits() {
        List<Habit> habits = habitService.readAllUserHabits(loggedUser);
        habits.forEach(System.out::println);
    }
}
