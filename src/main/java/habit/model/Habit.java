package habit.model;

import user.model.User;

import java.time.LocalDate;
import java.util.*;

public class Habit {
    private int id;
    private String name;
    private String description;
    private String frequency;
    private User user;
    private List<LocalDate> completionHistory;

    public Habit(String name, String description, String frequency, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.user = user;
        this.completionHistory = new ArrayList<>();
    }

    public void markCompletion(LocalDate date) {
        if (completionHistory == null) {
            completionHistory = new ArrayList<>(); // Инициализация, если еще не сделано
        }
        completionHistory.add(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<LocalDate> getCompletionHistory() {
        return completionHistory;
    }

    public void setCompletionHistory(List<LocalDate> completionHistory) {
        this.completionHistory = completionHistory;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", frequency='" + frequency + '\'' +
                ", user=" + user +
                ", completionHistory=" + completionHistory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return id == habit.id && Objects.equals(name, habit.name) &&
                Objects.equals(description, habit.description) &&
                Objects.equals(frequency, habit.frequency) &&
                Objects.equals(user, habit.user) &&
                Objects.equals(completionHistory, habit.completionHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, frequency, user, completionHistory);
    }
}
