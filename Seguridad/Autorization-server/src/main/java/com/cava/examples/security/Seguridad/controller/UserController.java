/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cava.examples.security.Seguridad.controller;

import com.cava.examples.common.entitis.Usuario;
import com.cava.examples.security.Seguridad.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cesar
 */
@RestController
public class UserController {

	@Autowired
	private UsuarioService service;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@PostMapping
	public ResponseEntity<?> saveUserPassword(@RequestBody Usuario user){
		String encoderp = passwordEncoder.encode(user.getPassword());
		user.setPassword(encoderp);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.saveuser(user));
	}
}
