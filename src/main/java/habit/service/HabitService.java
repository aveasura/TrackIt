package habit.service;

import habit.model.Habit;
import habit.repository.HabitRepository;
import user.model.User;

// todo доделать методы
public class HabitService {
    private HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }


    public void create(User loggedUser, Habit habit) {

        // +чек

        habitRepository.create(loggedUser, habit);
    }

    public Habit read(int id) {
        return habitRepository.read(id);
    }
}
