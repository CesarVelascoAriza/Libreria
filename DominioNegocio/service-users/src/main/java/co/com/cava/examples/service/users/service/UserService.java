package co.com.cava.examples.service.users.service;

import co.com.cava.examples.service.users.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Iterable<User> listUsers();
    Optional<User> findUserById(Long id);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(long id);
}

