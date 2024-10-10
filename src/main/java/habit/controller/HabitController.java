package habit.controller;

import habit.model.Habit;
import habit.service.HabitService;
import user.model.User;

import java.util.Scanner;

// todo доделать логику/методы
public class HabitController {
    private Scanner scanner = new Scanner(System.in);
    private HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    String choice;

    // меню управления привычками.
    public void showHabitMenu(User loggedUser) {
        System.out.println("Habit Manager\n" +
                "Choose what needs to be done:" +
                """
                        1. Create habit
                        2. Read habit info
                        3. Update habit
                        4. Delete habit
                        5. Show my all habits
                        """);
        choice = scanner.nextLine();

        switch (choice) {
            case "1" -> createHabit(loggedUser);
            case "2" -> readHabitInfo(loggedUser);
            case "3" -> updateHabit();
            case "4" -> deleteHabit();
            case "5" -> showAllUserHabits();
        }
    }

    private void createHabit(User loggedUser) {
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

    private void readHabitInfo(User loggedUser) {
        Habit habit = habitService.read(loggedUser.getId());
        System.out.println(habit);
    }

    private void updateHabit() {
    }

    private void deleteHabit() {
    }

    private void showAllUserHabits() {
    }


}
