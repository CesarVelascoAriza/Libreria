package com.cava.examples.seguridad.ususario;


import com.cava.examples.common.entitis.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "SERVICE-USERS")
public interface FeingUsuario {

    @GetMapping("/buscar-username")
    Usuario buscarPorUserName(@RequestParam("userName") String userName);

    @PostMapping
    ResponseEntity<Usuario> guarUsuarioConPass(@RequestBody Usuario user);
}
