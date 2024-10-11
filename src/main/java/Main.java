import habit.repository.InMemoryHabitRepository;
import habit.service.HabitService;
import user.controller.UserController;
import user.repository.InMemoryUserRepository;
import user.repository.UserRepository;
import user.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new InMemoryUserRepository();
        InMemoryHabitRepository habitRepository = new InMemoryHabitRepository();

        UserService userService = new UserService(userRepository);
        HabitService habitService = new HabitService(habitRepository);

        UserController userController = new UserController(userService, habitService);

        userController.run();
    }
}