package co.com.cava.examples.service.users.service;

import java.util.Optional;

import co.com.cava.examples.service.users.entity.User;

public interface UserService {

	Iterable<User> listUsers();

	Optional<User> findUserById(Long id);

	User createUser(User user);

	User updateUser(User user);

	void deleteUser(long id);

	Optional<User> findByName(String userName);

}
