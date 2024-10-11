package habit.service;

import habit.model.Habit;
import habit.repository.HabitRepository;
import user.model.User;


public class HabitService {
    private HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    // todo доделать методы + проверки
    public void create(User loggedUser, Habit habit) {
        habitRepository.create(loggedUser, habit);
    }

    public Habit habitInfo(int id) {
        return habitRepository.read(id);
    }

    public void readAllUserHabits(User loggedUser) {
        habitRepository.readAllByUserId(loggedUser.getId());
    }

    public void updateHabit(User loggedUser, int habitId) {
        Habit habit = habitRepository.read(habitId);
        habitRepository.update(loggedUser.getId(), habit);
    }

    public void deleteHabit(User user, int habitId) {
        Habit habit = habitRepository.read(habitId);

        // check null

        habitRepository.delete(user.getId(), habit);
    }
}
