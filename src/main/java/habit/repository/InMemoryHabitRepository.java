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
    public Set<Habit> readAllByUserId(int userId) {
        return userHabits.getOrDefault(userId, new HashSet<>());
    }


    @Override
    public void update(int id, Habit habit) {

    }

    @Override
    public void delete(int userId, Habit habit) {

    }
}
