package co.com.cava.examples.service.users.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.commons.users.entitis.User;

import co.com.cava.examples.service.users.repository.UserRespository;

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
          m.setUserName(user.getUserName());
          m.setPassword(user.getPassword());
          m.setRoles(user.getRoles());
          
          return  m;
        }).orElseThrow();
    }

    @Override
    public void deleteUser(long id) {
        userRespository.deleteById(id);
    }

    @Override
    public Optional<User> findByName(String userName) {
        return userRespository.findByUserName(userName);
    }
}
