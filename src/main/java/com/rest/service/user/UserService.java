package com.rest.service.user;

import com.rest.model.user.UserRole;
import com.rest.model.user.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    //create user with role
    User save(User user, Set<UserRole> userRoles) throws Exception;
    //save user
    User save(User user);
    //get single user
    User findByUsername(String username);
    //get all user
    List<User> findAll();
    //delete by id
    void deleteById(Long id);
    //find by id
    User findById(Long id);
}
