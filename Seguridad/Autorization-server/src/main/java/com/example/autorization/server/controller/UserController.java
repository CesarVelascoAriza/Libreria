package com.example.autorization.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.autorization.server.service.UsuarioService;
import com.example.commons.users.entitis.User;

@RestController
public class UserController {

	@Autowired
	private UsuarioService service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PostMapping
	public ResponseEntity<?> saveUserPassword(@RequestBody User user){
		String encoderp = passwordEncoder.encode(user.getPassword());
		user.setPassword(encoderp);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.saveuser(user));
	}
}
