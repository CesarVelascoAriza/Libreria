package co.com.cava.examples.service.users.controller;

import java.util.Optional;

import com.cava.examples.common.entitis.Usuario;
import com.cava.examples.controller.CommonController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import co.com.cava.examples.service.users.service.UserService;

@RestController
public class UserController extends CommonController<Usuario,UserService> {


	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Usuario user) {
		user.setId(id);
		return ResponseEntity.ok().body(service.updateUser(user));
	}
	@GetMapping("/buscar-username")
	public ResponseEntity<?> getUserName(@RequestParam String userName) {
		Optional<Usuario> userOptional = service.findByName(userName);
		if (!userOptional.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(userOptional.get());
	}

}
