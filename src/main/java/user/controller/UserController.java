package user.controller;

import habit.controller.HabitController;
import habit.service.HabitService;
import user.model.User;
import user.service.UserService;

import java.util.Scanner;


public class UserController {
    private static Scanner scanner = new Scanner(System.in);
    private UserService userService;
    private HabitService habitService;

    private User loggedInUser;
    private String choice;

    public UserController(UserService userService, HabitService habitService) {
        this.userService = userService;
        this.habitService = habitService;
    }

    public void run() {

        while (true) {
            System.out.println("Choose an option: ");
            System.out.println("1. Registration\n2. Login\n3. Exit");
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> register(scanner);
                case "2" -> login(scanner);
                case "3" -> System.exit(0);
                default -> System.out.println("Wrong choice");
            }
        }
    }

    private void register(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        String result = userService.registration(new User(name, email, password));
        System.out.println(result);
    }

    private void login(Scanner scanner) {
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        loggedInUser = userService.login(email, password);
        if (loggedInUser != null) {
            System.out.println("Authorization was successful");
            showUserMenu(scanner);
        } else {
            System.out.println("Incorrect email or password");
        }
    }

    private void showUserMenu(Scanner scanner) {
        while (true) {
            System.out.println("Hello " + loggedInUser.getName() + "!");
            System.out.println("""
                    Choose an option:
                    1. Change name
                    2. Change email
                    3. Change password
                    4. Manage habits
                    5. User Info
                    6. Log out
                    7. Delete account""");
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> updateName();
                case "2" -> updateEmail();
                case "3" -> updatePassword();
                case "4" -> manageHabits();
                case "5" -> showUserInfo();
                case "6" -> {
                    System.out.println("You have been logged out");
                    return;
                }
                case "7" -> deleteCurrentUserAccount();
                default -> System.out.println("Wrong choice");
            }
        }
    }

    // todo переместить логику в сервис
    private void updateName() {
        System.out.print("Enter a new name: ");
        String newName = scanner.nextLine();
        loggedInUser.setName(newName);
        userService.updateUser(loggedInUser);

        System.out.println("Name updated successfully");
    }

    private void updateEmail() {
        System.out.print("Enter new email: ");
        String newEmail = scanner.nextLine();
        loggedInUser.setEmail(newEmail);
        userService.updateUser(loggedInUser);

        System.out.println("Email updated successfully");
    }

    private void updatePassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        loggedInUser.setPassword(newPassword);
        userService.updateUser(loggedInUser);

        System.out.println("Password updated successfully");
    }

    private void showUserInfo() {
        User user = userService.userInfo(loggedInUser);
        if (user != null) {
            System.out.println("User Info:");
            System.out.println(user);
        } else {
            System.out.println("User not found.");
        }
    }

    private void deleteCurrentUserAccount() {
        System.out.println("Are you sure you want to delete your account?\n Yes / No");
        choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "yes" -> {
                userService.deleteUser(loggedInUser);
                System.out.println("Account deleted");
                System.out.println("You have been logged out.");
                loggedInUser = null;
                run();
            }
            case "no" -> System.out.println("You have cancelled the account deletion");
            default -> System.out.println("You need to select yes or no");
        }
    }


    private void manageHabits() {
        HabitController habitController = new HabitController(habitService);
        habitController.showHabitMenu(loggedInUser);
    }
}