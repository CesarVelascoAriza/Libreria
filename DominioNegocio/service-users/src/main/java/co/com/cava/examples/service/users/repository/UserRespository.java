package co.com.cava.examples.service.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.commons.users.entitis.User;

import java.util.Optional;

public interface UserRespository extends JpaRepository<User, Long> {
	
	Optional<User> findByUserName(String userName);

    Optional<User> findByUsername(String userName);
}
