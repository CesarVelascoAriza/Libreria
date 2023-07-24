package co.com.cava.examples.service.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.cava.examples.service.users.entity.User;

public interface UserRespository extends JpaRepository<User, Long> {

}
