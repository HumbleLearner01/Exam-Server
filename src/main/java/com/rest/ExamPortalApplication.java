package com.rest;

import com.rest.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ExamPortalApplication implements CommandLineRunner {
    @Autowired private UserService userService;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ExamPortalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("-----------Running the code!-----------");
//        try {
////            User user = new User();
////            user.setFirstName("admin");
////            user.setLastName("admin");
////            user.setEmail("java.email.humblelearner@gmail.com");
////            user.setUsername("admin");
////            user.setEnabled(true);
////            user.setPassword(bCryptPasswordEncoder.encode("admin"));
////            user.setPhone("09374740412");
////            user.setProfile("profile.png");
////
////            Role role = new Role();
////            role.setRoleId(2L);
////            role.setRoleName("ADMIN");
////
////            UserRole userRole = new UserRole();
////            userRole.setUser(user);
////            userRole.setRole(role);
////
////            Set<UserRole> userRoleSet = new HashSet<>();
////            userRoleSet.add(userRole);
////            userService.save(user, userRoleSet);
////            System.out.println(user.getUsername());
//        } catch (UserNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
