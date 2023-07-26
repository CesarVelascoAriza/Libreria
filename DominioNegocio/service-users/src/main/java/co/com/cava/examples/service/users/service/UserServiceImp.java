package co.com.cava.examples.service.users.service;

import co.com.cava.examples.service.users.entity.User;
import co.com.cava.examples.service.users.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRespository userRespository;
    @Override
    public Iterable<User> listUsers() {
        return userRespository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRespository.findById(id);
    }

    @Override
    public User createUser(User user) {
        return userRespository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRespository.findById(user.getId()).map(m->{
          m.setUsername(user.getUsername());
          m.setPassword(user.getPassword());
          return  m;
        }).orElseThrow();
    }

    @Override
    public void deleteUser(long id) {
        userRespository.deleteById(id);
    }

    @Override
    public Optional<User> findByName(String userName) {
        return userRespository.findByUsername(userName);
    }
}
