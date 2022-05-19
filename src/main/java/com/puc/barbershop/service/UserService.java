package com.puc.barbershop.service;

import com.puc.barbershop.model.Role;
import com.puc.barbershop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    Optional<User> getUser(String username);
    void deleteUser(User user);
    List<User> getUsers();

}
