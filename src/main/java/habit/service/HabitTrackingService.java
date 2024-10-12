package habit.service;

import habit.model.Habit;
import habit.repository.HabitRepository;
import java.time.LocalDate;

import java.util.*;

public class HabitTrackingService {
    private final Map<Integer, Set<LocalDate>> habitCompletionHistory = new HashMap<>();
    private final HabitRepository habitRepository;

    public HabitTrackingService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public void trackHabitCompletion(int userId, int habitId, LocalDate date) {
        Habit habit = habitRepository.read(habitId);

        if (habit != null && habit.getUser().getId() == userId) {
            habit.markCompletion(date); // todo

            habitCompletionHistory
                    .computeIfAbsent(habitId, k -> new HashSet<>())
                    .add(date);

            System.out.println("Habit marked as completed for date: " + date);
        } else {
            System.out.println("Habit not found or does not belong to the user.");
        }
    }

    public List<LocalDate> getCompletionHistory(int habitId) {
        Set<LocalDate> completionHistory = habitCompletionHistory.getOrDefault(habitId, Collections.emptySet());

        // Преобразование Set в List
        return new ArrayList<>(completionHistory);
    }
}

