package com.rest.config.jwt.userdetailsservice;

import com.rest.model.user.User;
import com.rest.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            System.out.println("<<<<<<<<<< user not found (UserDetailsServiceImpl) >>>>>>>>>>");
            throw new UsernameNotFoundException("<<<<<<<<<< user not found (UserDetailsServiceImpl) >>>>>>>>>>");
        }
        return user;
    }
}
