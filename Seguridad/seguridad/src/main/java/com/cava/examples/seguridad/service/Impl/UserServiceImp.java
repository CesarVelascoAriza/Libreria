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
public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    private FeingUsuario usuarioFeing;

    @Override
    public User userinfoByName(String userName) {
        System.out.println("por aca paso");
        Usuario usuario = userfindByName(userName);
        if(usuario == null)
            throw new UsernameNotFoundException("Error en el login usuario no existe " + userName);
        System.out.println("por aca paso"+  usuario.getUserName());
        List<GrantedAuthority> autirities = usuario.getRoles().stream()
                .map(roles->new SimpleGrantedAuthority(roles.getNombreRol()))
                .collect(Collectors.toList());

        return  new User(usuario.getUserName(),usuario.getPassword(),usuario.isEnabled(),true,true,true,autirities);
    }


    public Usuario userfindByName(String userName) {
        // TODO Auto-generated method stub
        return usuarioFeing.buscarPorUserName(userName);
    }

    @Override
    public Usuario saveUser(Usuario user) {
        ResponseEntity<Usuario> usuariodb = usuarioFeing.guarUsuarioConPass(user);
        return usuariodb.getBody();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) usuarioFeing.buscarPorUserName(username);
    }
}
