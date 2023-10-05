package co.com.cava.examples.service.users.controller;

import java.util.Optional;

import com.cava.examples.common.entitis.Usuario;
import com.cava.examples.controller.CommonController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import co.com.cava.examples.service.users.service.UserService;

@RestController
public class UserController extends CommonController<Usuario,UserService> {

	@GetMapping("/buscar-username")
	public ResponseEntity<?> getUserName(@RequestParam String userName) {
		Optional<Usuario> userOptional = service.findByName(userName);
		if (!userOptional.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(userOptional.get());
	}

}
