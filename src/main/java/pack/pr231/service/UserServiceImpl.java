package pack.pr231.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pack.pr231.model.User;
import pack.pr231.repository.UserRepository;

import javax.servlet.Registration;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Controller
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Transactional
    @Override
    public User findUser(long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(long id, User user) {
        userRepository.update(id, user.getEmail(), user.getNickname(), user.getPts());
    }


}
