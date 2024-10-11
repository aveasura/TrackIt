package habit.controller;

import habit.model.Habit;
import habit.service.HabitService;
import user.model.User;

import java.util.Scanner;

// todo доделать логику/методы
public class HabitController {
    private Scanner scanner;
    private HabitService habitService;
    private User loggedUser;
    String choice;

    public HabitController(HabitService habitService, User loggedUser) {
        this.habitService = habitService;
        this.loggedUser = loggedUser;
        this.scanner = new Scanner(System.in);
    }

    public void showHabitMenu() {
        System.out.println("Habit Manager\n" +
                "Choose what needs to be done:\n" + """
                        1. Create habit
                        2. Show my habits
                        3. Update habit
                        4. Delete habit
                        """);
        choice = scanner.nextLine();

        switch (choice) {
            case "1" -> createHabit();
            case "2" -> myHabits();
            case "3" -> updateHabit();
            case "4" -> deleteHabit();
            default -> System.out.println("Wrong choice");
        }
    }

    private void createHabit() {
        System.out.println("Habit title");
        String title = scanner.nextLine();
        System.out.println("Habit description");
        String description = scanner.nextLine();
        System.out.println("Habit frequency (test)");
        String frequency = scanner.nextLine();

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
        habitToUpdate.setDescription(choice);

        habitService.updateHabit(loggedUser, habitToUpdate.getId());
    }

    private void deleteHabit() {
        System.out.println("Choice id habit for delete");
        myHabits();
        int habitId = Integer.parseInt(scanner.nextLine());
        habitService.deleteHabit(loggedUser, habitId);
    }

    private void myHabits() {
        System.out.println("\nAll your habits:");
        habitService.readAllUserHabits(loggedUser);
    }
}
