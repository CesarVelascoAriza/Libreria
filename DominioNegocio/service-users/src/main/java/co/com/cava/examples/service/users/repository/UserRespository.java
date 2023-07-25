package co.com.cava.examples.service.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.cava.examples.service.users.entity.User;

import java.util.Optional;

public interface UserRespository extends JpaRepository<User, Long> {

    Optional<User> findUserName(String userName);
}
