package kz.frosys.kapp.service;

import javassist.NotFoundException;
import kz.frosys.kapp.model.Role;
import kz.frosys.kapp.model.Status;
import kz.frosys.kapp.model.User;
import kz.frosys.kapp.repository.RoleRepository;
import kz.frosys.kapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final  UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User newUser) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(userRoles);
        newUser.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(newUser);
        log.info("IN register - user:{} successfully registered",registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();

        log.info("IN getAll() - {} users found");
        return result;
    }

    @Override
    public User findByUsername(String name) {
        User result = userRepository.findByUsername(name);
        log.info("IN findByUsername - user: {} found by username: {} ",result,name);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        log.info("IN findById - user: {} found by id: {} ",result,id);
        return result;
    }

    @Override
    public User update(User user) {

        User userFromDb = userRepository.findById(user.getId()).orElse(null);

        if(userFromDb == null){
            throw new UsernameNotFoundException("User with id: " + user.getId() + " not found");
        }

        user.setCreated(userFromDb.getCreated());
        user.setRoles(userFromDb.getRoles());
        user.setPassword(userFromDb.getPassword());

        User updatedUser = userRepository.save(user);
        log.info("IN update - user:{} successfully updated",updatedUser);

        return updatedUser;
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElse(null);

        user.setStatus(Status.NOT_ACTIVE);

        userRepository.save(user);
    }

    @Override
    public void addRoleToUser(Long roleId,Long userId) throws NotFoundException {
        Role role = roleRepository.findById(roleId).orElse(null);

        if(role == null){
            throw new NotFoundException("Role with id:" + roleId + " not found");
        }

        userRepository.findById(userId).ifPresent(user -> {
            role.getUsers().add(user);
            roleRepository.save(role);
        });

    }

    @Override
    public void deleteRoleFromUser(Long roleId, Long userId) throws NotFoundException {
        Role role = roleRepository.findById(roleId).orElse(null);

        if(role == null){
            throw new NotFoundException("Role with id:" + roleId + " not found");
        }


        role.getUsers().remove(userId);
        roleRepository.save(role);
    }
}
