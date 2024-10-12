package habit.service;

import habit.model.Habit;
import habit.repository.HabitRepository;
import user.model.User;

import java.util.List;


public class HabitService {
    private HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public void create(User loggedUser, Habit habit) {
        habitRepository.create(loggedUser.getId(), habit);
    }

    public Habit habitInfo(int id) {
        return habitRepository.read(id);
    }

    public List<Habit> readAllUserHabits(User loggedUser) {
        return habitRepository.readAllByUserId(loggedUser.getId());
    }


    public void updateHabit(User loggedUser, Habit updatedHabit) {
        habitRepository.update(loggedUser.getId(), updatedHabit);
    }

    public void deleteHabit(User user, int habitId) {
        Habit habit = habitRepository.read(habitId);

        if (habit == null || habit.getUser().getId() != user.getId()) {
            System.out.println("Habit not found or does not belong to the user.");
            return;
        }

        habitRepository.delete(user.getId(), habit);
    }
}
