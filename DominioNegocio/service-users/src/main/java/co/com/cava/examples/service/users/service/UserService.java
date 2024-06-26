package co.com.cava.examples.service.users.service;

import java.util.Optional;

import com.cava.examples.common.entitis.Usuario;
import com.cava.examples.service.CommonService;


public interface UserService extends CommonService<Usuario> {


    Usuario updateUser(Usuario usuario);
    Optional<Usuario> findByName(String userName);
}

