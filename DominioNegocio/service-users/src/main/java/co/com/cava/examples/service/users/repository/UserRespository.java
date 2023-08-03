package co.com.cava.examples.service.users.repository;

import java.util.Optional;

import com.cava.examples.commons.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User, Long> {
	
	Optional<User> findByUserName(String username);

}
