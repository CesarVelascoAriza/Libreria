package co.com.cava.examples.service.users.controller;

import co.com.cava.examples.service.users.dto.UserDTO;
import co.com.cava.examples.service.users.entity.User;
import co.com.cava.examples.service.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    @GetMapping("/{id}")
    public  ResponseEntity<?> getUserById(@PathVariable Long id){
        Optional<User> isUser = service.findUserById(id);
        if(isUser.isPresent())
            return ResponseEntity.ok().body(isUser.get());
        else
            return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO user, BindingResult result){
        if (result.hasErrors()) {
            return this.validar(result);
        }else {
        User user1= new User();
        user1.setUsername(user.getUserName());
        user1.setPassword(user.getPassword());
        return  ResponseEntity.ok().body(service.createUser(user1));}
    }
    @PutMapping("/{id}")
    public  ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user){
        user.setId(id);
        return  ResponseEntity.ok().body(service.updateUser(user));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search-username")
    public ResponseEntity<?> getUserName(@RequestParam String userName){
        Optional<User> userOptional =service.findByName(userName);
        if (!userOptional.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(userOptional.get());
    }
    protected ResponseEntity<?> validar(BindingResult result){
        Map<String,Object> errors= new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errors.put(err.getField(),"el atributo"+ err.getField() + " " + err.getDefaultMessage() );
        });
        return  ResponseEntity.badRequest().body(errors);
    }

}
