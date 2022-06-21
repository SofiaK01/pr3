package pack.pr231.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pack.pr231.model.Role;
import pack.pr231.model.User;
import pack.pr231.repository.RoleRepository;
import pack.pr231.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional

    public void add(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        for (Role r : user.getRoles()) {
            roles.add(roleRepository.findRoleByName(r.getName()));
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Transactional
    public User findUserById(int id) {
        return userRepository.findById(id).get();
    }


    public User findUserByNickname(String nickname) {
        return userRepository.findUserByNickname(nickname);
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public List<User> listUsers() {
        return userRepository.findAll();
    }


    public void updateUser(int id, User user) {

        if (user.getPassword().isEmpty()){
            user.setPassword(this.findUserById(id).getPassword());
        } else if (!user.getPassword().equals(this.findUserById(id).getPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userRepository.findUserByNickname(nickname);
        if (user == null) {
            throw new UsernameNotFoundException("User with name " + nickname + " not found");
        }
        return user;
    }
}

