package com.rest.controller.user;

import com.rest.model.user.UserRole;
import com.rest.service.MailService;
import com.rest.model.user.Role;
import com.rest.model.user.ConfirmToken;
import com.rest.model.user.User;
import com.rest.repository.user.ConfirmTokenRepository;
import com.rest.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService emailService;
    private final ConfirmTokenRepository confirmTokenRepo;

    //creating user
    @PostMapping("")
    public User saveUser(@RequestBody User user) throws Exception {
        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("NORMAL");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRole);

        user.setProfile("default.png");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userService.save(user, userRoles);
    }

    //find all the user
    @GetMapping("")
    public List<User> findAll() {
        return userService.findAll();
    }

    //find a user by username
    @GetMapping("/{username}")
    public User findByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    //delete user by id
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }


    /**
     * @Activating-User-implementation
     **/ /*//////////////////////////////////////*/
    //sending the activation code to email
    @PostMapping("/activate/{id}")
    public @ResponseBody ResponseEntity<?> sendActivationCode(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        ConfirmToken confirmToken = new ConfirmToken(user);
        confirmTokenRepo.save(confirmToken);
        emailService.sendEmail(
                user.getEmail(),
                "Click here:   http://localhost:8080/user/confirm-account?token=" + confirmToken.getToken(),
                "Activate Your Account!");
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    //confirm the account
    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> activateAccount(@RequestParam("token") String token) {
        ConfirmToken confirmedToken = confirmTokenRepo.findByToken(token);
        if (confirmedToken != null) {
            User user = userService.findByUsername(confirmedToken.getUser().getUsername());
            user.setEnabled(true);
            userService.save(user);
            return new ResponseEntity<>("account activated", HttpStatus.OK);
        } else return new ResponseEntity<>("token is broken", HttpStatus.NOT_ACCEPTABLE);
    }
}