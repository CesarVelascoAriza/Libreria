package com.example.autorization.server.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.autorization.server.config.usuario.FeingUsuario;
import com.example.commons.users.entitis.User;

@Service
public class UsuarioServiceImp implements UsuarioService,UserDetailsService {
	
	@Autowired
	private FeingUsuario usuario;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User usuario = userfindByName(username);
		if(usuario == null)
			throw new UsernameNotFoundException("Error en el login usuario no existe " + username);
		List<GrantedAuthority> autirities = usuario.getRoles().stream()
				.map(roles->new SimpleGrantedAuthority(roles.getNombreRol()))
				.collect(Collectors.toList());
				
		return  new org.springframework.security.core.userdetails.User(usuario.getUserName(),usuario.getPassword(),usuario.isEnabled(),true,true,true,autirities);
	}

	@Override
	public User userfindByName(String userName) {
		// TODO Auto-generated method stub
		return usuario.buscarPorUserName(userName);
	}

	@Override
	public User saveuser(User user) {
		ResponseEntity<User> usuariodb = usuario.guarUsuarioConPass(user); 
		return usuariodb.getBody();
	}

	
}
