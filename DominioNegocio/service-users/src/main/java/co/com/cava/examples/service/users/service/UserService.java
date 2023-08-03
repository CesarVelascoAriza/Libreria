package co.com.cava.examples.service.users.service;

import com.cava.examples.commons.users.models.User;

import java.util.Optional;



public interface UserService {

	Iterable<User> listUsers();

	Optional<User> findUserById(Long id);

	User createUser(User user);

	User updateUser(User user);

	void deleteUser(long id);

	Optional<User> findByName(String userName);

}
