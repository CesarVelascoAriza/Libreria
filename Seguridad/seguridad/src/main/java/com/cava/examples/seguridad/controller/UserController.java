package com.cava.examples.seguridad.controller;


import com.cava.examples.common.entitis.Usuario;
import com.cava.examples.seguridad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    /*@Autowired
    private UserService service;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping
    public ResponseEntity<?> saveUserPassword(@RequestBody Usuario user){
        String encoderp = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoderp);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(user));
    }*/
}
