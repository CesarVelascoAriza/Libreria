/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cava.examples.security.Seguridad.usuario;

import com.cava.examples.common.entitis.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author cesar
 */
@FeignClient(name = "service-users")
public interface FeingUsuario {

    @GetMapping("/buscar-username")
    Usuario buscarPorUserName(@RequestParam("userName") String userName);

    @PostMapping
    ResponseEntity<Usuario> guarUsuarioConPass(@RequestBody Usuario user);
}
