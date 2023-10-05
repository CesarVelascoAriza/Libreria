package co.com.cava.examples.service.users.repository;

import java.util.Optional;

import com.cava.examples.common.entitis.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRespository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByUserName(String userName);

}
