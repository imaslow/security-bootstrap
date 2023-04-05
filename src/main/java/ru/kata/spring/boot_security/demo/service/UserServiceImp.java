package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImp implements UserService, UserDetailsService {
    private UserDao userDao;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    public UserServiceImp(UserDao userDao, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user found with username: "+ email);
        }
        return User.fromUser(user);
    }
    @Override
    public void createUser(User user) {
        User userToSave = new User();
        userToSave.setFirstname(user.getFirstname());
        userToSave.setLastname(user.getLastname());
        userToSave.setAge(user.getAge());
        userToSave.setEmail(user.getEmail());
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        userToSave.setRoles(user.getRoles());
        userDao.createUser(userToSave);
    }

    @Override
    public User findUserByName(String name) {
        return userDao.findByEmail(name);
    }
    public Optional<User> findUser(Long id){
        return Optional.ofNullable(userDao.findById(id));
    }

    @Override
    public Set<Role> getSetOfRoles(List<String> rolesId){
        Set<Role> roleSet = new HashSet<>();
        for (String id: rolesId) {
            roleSet.add(roleService.getRoleById(Long.parseLong(id)));
        }
        return roleSet;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }
    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }
    @Override
    public User readUser(long id) {
        return userDao.readUser(id);
    }

    @Override
    public User deleteUser(long id) {
        User user = null;
        try {
            user = userDao.deleteUser(id);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return user;
    }
}
