package com.rest.service.user.impl;

import com.rest.helper.exception.UserNotFoundException;
import com.rest.model.user.UserRole;
import com.rest.service.user.UserService;
import com.rest.repository.user.RoleRepository;
import com.rest.model.user.User;
import com.rest.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;

    @Override
    public User save(User user, Set<UserRole> userRoles) throws Exception {
        User u = userRepo.findByUsername(user.getUsername());
        if (u != null)
            throw new UserNotFoundException("\n\t\t\t<<<<<<<<<< User already exists >>>>>>>>>>\n");
        else {
            for (UserRole ur : userRoles)
                roleRepo.save(ur.getRole());
            user.getUserRoles().addAll(userRoles);
            u = userRepo.save(user);
        }
        return u;
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).get();
    }
}