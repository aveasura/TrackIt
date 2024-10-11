package habit.repository;

import habit.model.Habit;
import user.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// todo доделать методы
public class InMemoryHabitRepository implements HabitRepository {

    private final Map<User, Set<Habit>> userHabits = new HashMap<>();
    private int habitIdCounter;


    @Override
    public void create(User user, Habit habit) {
        habit.setId(habitIdCounter++);
        userHabits.computeIfAbsent(user, k -> new HashSet<>()).add(habit);
    }

    @Override
    public Habit read(int habitId) {
        return userHabits.values().stream()
                .flatMap(Set::stream)
                .filter(habit -> habit.getId() == habitId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void readAllByUserId(int userId) {
        userHabits.entrySet().stream()
                .filter(entry -> entry.getKey().getId() == userId)
                .flatMap(entry -> entry.getValue().stream())
                .forEach(System.out::println);
    }

    @Override
    public void update(int userId, Habit updatedHabit) {
        // Находим пользователя по userId
        User user = findUserById(userId);

        if (user != null) {
            Set<Habit> habits = userHabits.get(user);

            if (habits != null) {
                for (Habit habit : habits) {
                    if (habit.getId() == updatedHabit.getId()) {
                        // Обновляем поля привычки
                        habit.setName(updatedHabit.getName());
                        habit.setDescription(updatedHabit.getDescription());
//                        habit.setStatus(updatedHabit.getStatus());
                        System.out.println("Habit updated successfully");
                        return;
                    }
                }
                System.out.println("Habit not found.");
            } else {
                System.out.println("No habits found for the user");
            }
        } else {
            System.out.println("User not found.");
        }
    }


    @Override
    public void delete(int userId, Habit habit) {
        User user = findUserById(userId);

        if (user != null) {
            Set<Habit> habits = userHabits.get(user);

            if (habits != null && habits.contains(habit)) {
                habits.remove(habit);
                System.out.println("Habit successfully deleted.");
            } else {
                System.out.println("Habit not found for the user.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    public User findUserById(int userId) {
        for (User user : userHabits.keySet()) {
            if (user.getId() == userId) {
                return user;
            }
        }
        // если не найден
        return null;
    }

}
