package com.example.autorization.server.service;

import com.example.commons.users.entitis.User;

public interface UsuarioService {

	User userfindByName(String userName);
	User saveuser(User user);
	
}
