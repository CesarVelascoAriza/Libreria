package com.example.autorization.server.config.usuario;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.commons.users.entitis.User;

@FeignClient(name = "service-users")
public interface FeingUsuario {

	@GetMapping("/buscar-username")
	User buscarPorUserName(@RequestParam("userName") String userName);
	@PostMapping
	ResponseEntity<User> guarUsuarioConPass(@RequestBody User user);
}
