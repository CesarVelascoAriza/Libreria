package co.com.cava.examples.service.users.controller;

import co.com.cava.examples.service.users.entity.User;
import co.com.cava.examples.service.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<?> getAllUser(){
        Iterable<User> listado = service.listUsers();
        if (listado == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listado);
    }
}
