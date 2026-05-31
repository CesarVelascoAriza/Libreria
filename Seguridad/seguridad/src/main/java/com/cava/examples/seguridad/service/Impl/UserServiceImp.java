package com.cava.examples.seguridad.service.Impl;

import com.cava.examples.common.entitis.Usuario;
import com.cava.examples.seguridad.service.UserService;
import com.cava.examples.seguridad.ususario.FeingUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserDetailsService {

    @Autowired
    private FeingUsuario feingUsuario;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(">>> Intentando buscar al usuario en la base de datos: " + username);

        Usuario usuario = feingUsuario.buscarPorUserName(username);
        if(usuario == null)
            throw new UsernameNotFoundException("Error en el login usuario no existe " + username);
        System.out.println("por aca paso"+  usuario.getUserName());
        List<GrantedAuthority> autirities = usuario.getRoles().stream()
                .map(roles->new SimpleGrantedAuthority(roles.getNombreRol()))
                .collect(Collectors.toList());

        return  new User(usuario.getUserName(),usuario.getPassword(),usuario.isEnabled(),true,true,true,autirities);
    }
}
