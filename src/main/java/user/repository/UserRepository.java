package user.repository;

import user.model.User;

public interface UserRepository {

    void create(User user);

    User read(int id);

    void update(int id, User user);

    void delete(int id);

    User findByEmail(String email);

    User authentication(String email, String password);
}
