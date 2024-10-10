package habit.repository;

import habit.model.Habit;
import user.model.User;

import java.util.Set;

public interface HabitRepository {
    void create(User user, Habit habit);
    Habit read(int userId);
    Set<Habit> readAllByUserId(int userId);
    void update(int userId, Habit habit);
    void delete(int userId, Habit habit);

}
