package habit.repository;

import habit.model.Habit;
import java.util.List;

public interface HabitRepository {
    void create(int userId, Habit habit);

    Habit read(int habitId);

    List<Habit> readAllByUserId(int userId);

    void update(int userId, Habit habit);

    void delete(int userId, Habit habit);

}
