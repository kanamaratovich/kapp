package kz.frosys.kapp.service;

import javassist.NotFoundException;
import kz.frosys.kapp.model.User;

import java.util.List;

public interface UserService {
    User register(User newUser);
    List<User> getAll();
    User findByUsername(String name);
    User findById(Long id);
    User update(User user);
    void addRoleToUser(Long roleId, Long userId) throws NotFoundException;

    void delete(Long id);
    void deleteRoleFromUser(Long roleId, Long userId) throws NotFoundException;
}
