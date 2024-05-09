package co.com.cava.examples.service.users.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.cava.examples.common.entitis.Usuario;
import com.cava.examples.service.impl.CommonServiceImp;
import org.springframework.stereotype.Service;

import co.com.cava.examples.service.users.repository.UserRespository;

@Service
public class UserServiceImp extends CommonServiceImp<Usuario,UserRespository> implements UserService {

    @Override
    public Usuario updateUser(Usuario usuario) {
       Optional<Usuario> optional = repository.findById(usuario.getId()).map(p->{
          p.setUserName(usuario.getUserName());
          p.setPassword(usuario.getPassword());
          p.setRoles(usuario.getRoles());
          p.setEnabled(usuario.isEnabled());
          return p;
        });

        return repository.save(optional.get());
    }

    @Override
    public Optional<Usuario> findByName(String userName) {
        return repository.findByUserName(userName);
    }
}
