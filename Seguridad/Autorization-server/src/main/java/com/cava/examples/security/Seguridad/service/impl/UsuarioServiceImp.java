/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cava.examples.security.Seguridad.service.impl;

/**
 *
 * @author cesar
 */
import com.cava.examples.common.entitis.Usuario;
import com.cava.examples.security.Seguridad.service.UsuarioService;
import com.cava.examples.security.Seguridad.usuario.FeingUsuario;

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


@Service
public class UsuarioServiceImp implements UsuarioService,UserDetailsService {
	
	@Autowired
	private FeingUsuario usuarioFeing;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = userfindByName(username);
		if(usuario == null)
			throw new UsernameNotFoundException("Error en el login usuario no existe " + username);
		List<GrantedAuthority> autirities = usuario.getRoles().stream()
				.map(roles->new SimpleGrantedAuthority(roles.getNombreRol()))
				.collect(Collectors.toList());
				
		return  new org.springframework.security.core.userdetails.User(usuario.getUserName(),usuario.getPassword(),usuario.isEnabled(),true,true,true,autirities);
	}

	@Override
	public Usuario userfindByName(String userName) {
		// TODO Auto-generated method stub
		return usuarioFeing.buscarPorUserName(userName);
	}

	@Override
	public Usuario saveuser(Usuario user) {
		ResponseEntity<Usuario> usuariodb = usuarioFeing.guarUsuarioConPass(user); 
		return usuariodb.getBody();
	}

	
}
