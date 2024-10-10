package user.service;

import user.model.User;
import user.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registration(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            return "A user with this email already exists";
        }

        userRepository.create(user);
        return "Registration was successful";
    }

    public User login(String email, String password) {
        return userRepository.authentication(email, password);
    }

    public User userInfo(User loggedInUser) {
        return userRepository.read(loggedInUser.getId());
    }

    public void updateUser(User loggedInUser) {
        userRepository.update(loggedInUser.getId(), loggedInUser);
    }

    public void deleteUser(User loggedInUser) {
        userRepository.delete(loggedInUser.getId());
    }
}
