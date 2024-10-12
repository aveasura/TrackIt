package habit.service;

import habit.model.Habit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HabitAnalyticsService {
    private final HabitTrackingService trackingService;

    public HabitAnalyticsService(HabitTrackingService trackingService) {
        this.trackingService = trackingService;
    }

    public int calculateCurrentStreak(int habitId) {
        List<LocalDate> completionDates = trackingService.getCompletionHistory(habitId);

        if (completionDates.isEmpty()) {
            return 0; // Если нет выполнений, возвращаем 0
        }

        // Сортируем и упорядочиваем даты выполнения по убыванию (от самой последней к первой)
        List<LocalDate> sortedDates = new ArrayList<>(completionDates);
        sortedDates.sort(Comparator.reverseOrder());

        int streak = 1; // Начинаем с 1, так как первая дата уже является частью серии
        LocalDate previousDate = sortedDates.get(0);

        // Идем по отсортированным датам, начиная с самой последней выполненной
        for (int i = 1; i < sortedDates.size(); i++) {
            LocalDate currentDate = sortedDates.get(i);
            // Проверяем, есть ли последовательность
            if (previousDate.minusDays(1).equals(currentDate)) {
                streak++;
                previousDate = currentDate; // Обновляем дату для следующей проверки
            } else {
                break; // Прерываем, если последовательность нарушена
            }
        }

        return streak;
    }

    public void generateHabitReport(Habit habit, LocalDate startDate, LocalDate endDate) {
        double completionRate = calculateCompletionRate(habit, startDate, endDate);
        int streak = calculateCurrentStreak(habit.getId());

        System.out.println("Report for Habit: " + habit.getName());
        System.out.println("Period: " + startDate + " to " + endDate);
        System.out.println("Completion rate: " + String.format("%.2f", completionRate) + "%");
        System.out.println("Current streak: " + streak + " days");
    }

    public double calculateCompletionRate(Habit habit, LocalDate startDate, LocalDate endDate) {
        List<LocalDate> history = habit.getCompletionHistory();

        if (history.isEmpty() || ChronoUnit.DAYS.between(startDate, endDate) < 0) {
            return 0; // Проверка на валидность периода
        }

        long completedDays = history.stream()
                .filter(date -> !date.isBefore(startDate) && !date.isAfter(endDate))
                .count();

        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        if (totalDays == 0) {
            return 0; // Избегаем деления на 0
        }

        return (double) completedDays / totalDays * 100;
    }

    public String generateAnalytics(int habitId, String period) {
        List<LocalDate> completionHistory = trackingService.getCompletionHistory(habitId);

        if (completionHistory.isEmpty()) {
            return "No completion history found for this habit.";
        }

        LocalDate startDate = calculateStartDate(period);
        LocalDate endDate = LocalDate.now();
        int totalDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1; // общее количество дней

        long completedDays = completionHistory.stream()
                .filter(date -> !date.isBefore(startDate) && !date.isAfter(endDate))
                .count();

        double completionRate = ((double) completedDays / totalDays) * 100;
        int currentStreak = calculateCurrentStreak(habitId);

        return String.format(
                "Analytics for habit ID %d:%n- Completion Rate: %.2f%%%n- Current Streak: %d days",
                habitId, completionRate, currentStreak
        );
    }

    private LocalDate calculateStartDate(String period) {
        LocalDate today = LocalDate.now();
        return switch (period.toLowerCase()) {
            case "daily" -> today;
            case "weekly" -> today.minusWeeks(1);
            case "monthly" -> today.minusMonths(1);
            default -> throw new IllegalArgumentException("Invalid period: " + period);
        };
    }

    private int calculateTotalDays(String period) {
        return switch (period.toLowerCase()) {
            case "daily" -> 1;
            case "weekly" -> 7;
            case "monthly" -> 30;  // Условно принимаем, что в месяце 30 дней
            default -> throw new IllegalArgumentException("Invalid period: " + period);
        };
    }
}
