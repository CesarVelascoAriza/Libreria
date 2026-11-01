package com.cava.examples.seguridad.controller;

import com.cava.examples.common.entitis.Usuario;
import com.cava.examples.seguridad.ususario.FeingUsuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entrada de escritura de usuarios: cifra la clave antes de delegar al servicio de usuarios.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final FeingUsuario feingUsuario;
    private final PasswordEncoder passwordEncoder;

    public UserController(FeingUsuario feingUsuario, PasswordEncoder passwordEncoder) {
        this.feingUsuario = feingUsuario;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Usuario user) {
        if (!tienePassword(user)) {
            return ResponseEntity.badRequest().body("La clave es obligatoria");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return feingUsuario.guarUsuarioConPass(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Usuario user) {
        if (!tienePassword(user)) {
            return ResponseEntity.badRequest().body("La clave es obligatoria");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return feingUsuario.actualizarUsuario(id, user);
    }

    private boolean tienePassword(Usuario user) {
        return user.getPassword() != null && !user.getPassword().isBlank();
    }
}
