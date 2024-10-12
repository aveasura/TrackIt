package habit.repository;

import habit.model.Habit;
import java.util.*;

public class InMemoryHabitRepository implements HabitRepository {

    private final Map<Integer, Set<Habit>> userHabits = new HashMap<>();
    private int habitIdCounter;


    @Override
    public void create(int userId, Habit habit) {
        habit.setId(habitIdCounter++);
        userHabits.computeIfAbsent(userId, k -> new HashSet<>()).add(habit);
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
    public List<Habit> readAllByUserId(int userId) {
        return new ArrayList<>(userHabits.getOrDefault(userId, Collections.emptySet()));
    }

    @Override
    public void update(int userId, Habit updatedHabit) {
        Set<Habit> habits = userHabits.get(userId);

        if (habits != null) {
            for (Habit habit : habits) {
                if (habit.getId() == updatedHabit.getId()) {
                    habit.setName(updatedHabit.getName());
                    habit.setDescription(updatedHabit.getDescription());
                    System.out.println("Habit updated successfully");
                    return;
                }
            }
            System.out.println("Habit not found.");
        } else {
            System.out.println("No habits found for the user.");
        }
    }

    @Override
    public void delete(int userId, Habit habit) {
        Set<Habit> habits = userHabits.get(userId);

        if (habits != null) {
            Habit habitToDelete = habits.stream()
                    .filter(h -> h.getId() == habit.getId())
                    .findFirst()
                    .orElse(null);

            if (habitToDelete != null) {
                habits.remove(habitToDelete);
                System.out.println("Habit successfully deleted.");
            } else {
                System.out.println("Habit not found for the user.");
            }
        } else {
            System.out.println("No habits found for the user.");
        }
    }

}
