import habit.repository.InMemoryHabitRepository;
import habit.service.HabitAnalyticsService;
import habit.service.HabitService;
import habit.service.HabitTrackingService;
import user.controller.UserController;
import user.repository.InMemoryUserRepository;
import user.repository.UserRepository;
import user.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new InMemoryUserRepository();
        InMemoryHabitRepository habitRepository = new InMemoryHabitRepository();

        HabitService habitService = new HabitService(habitRepository);
        UserService userService = new UserService(userRepository);

        HabitTrackingService trackingService = new HabitTrackingService(habitRepository);
        HabitAnalyticsService analyticsService = new HabitAnalyticsService(trackingService);

        UserController userController = new UserController(userService, habitService, trackingService, analyticsService);

        userController.run();
    }
}