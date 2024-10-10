package user.repository;

import user.model.User;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter;

    @Override
    public void create(User user) {
        user.setId(idCounter++);
        users.put(user.getId(), user);
    }

    @Override
    public User read(int id) {
        return users.get(id);
    }

    @Override
    public void update(int id, User updatedUser) {
        if (users.containsKey(id)) {
            User existingUser = users.get(id);

            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());

        } else System.out.println("No such user found");
    }

    @Override
    public void delete(int id) {
        users.remove(id);
    }

    @Override
    public User findByEmail(String email) {
        return users.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User authentication(String email, String password) {
        return users.values()
                .stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
