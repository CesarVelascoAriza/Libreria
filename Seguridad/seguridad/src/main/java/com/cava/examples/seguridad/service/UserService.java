package com.cava.examples.seguridad.service;

import com.cava.examples.common.entitis.Usuario;
import org.springframework.security.core.userdetails.User;

public interface UserService {

    User userinfoByName(String userName);

    Usuario saveUser(Usuario user);
}
