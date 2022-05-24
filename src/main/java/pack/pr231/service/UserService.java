package pack.pr231.service;

import pack.pr231.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(User user);

    User findUser(long id);

    void delete(long id);

    List<User> listUsers();

    void updateUser(long id, User user);
}
