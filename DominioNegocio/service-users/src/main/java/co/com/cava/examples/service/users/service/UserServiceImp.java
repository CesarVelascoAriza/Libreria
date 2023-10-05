package co.com.cava.examples.service.users.service;

import java.util.Optional;

import com.cava.examples.common.entitis.Usuario;
import com.cava.examples.service.impl.CommonServiceImp;
import org.springframework.stereotype.Service;

import co.com.cava.examples.service.users.repository.UserRespository;

@Service
public class UserServiceImp extends CommonServiceImp<Usuario,UserRespository> implements UserService {

    public Usuario updateUser(Usuario user) {
        return repository.findById(user.getId()).map(m->{
          m.setUserName(user.getUserName());
          m.setPassword(user.getPassword());
          m.setRoles(user.getRoles());
          
          return  m;
        }).orElseThrow();
    }
    @Override
    public Optional<Usuario> findByName(String userName) {
        return repository.findByUserName(userName);
    }
}
