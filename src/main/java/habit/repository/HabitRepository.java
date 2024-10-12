package habit.repository;

import habit.model.Habit;

import java.time.LocalDate;
import java.util.List;

public interface HabitRepository {
    void create(int userId, Habit habit);

    Habit read(int habitId);

    List<Habit> readAllByUserId(int userId);

    void update(int userId, Habit habit);

    void delete(int userId, Habit habit);

    void markHabitCompletion(int userId, int habitId, LocalDate date);

    List<LocalDate> getHabitCompletionHistory(int habitId);
}
