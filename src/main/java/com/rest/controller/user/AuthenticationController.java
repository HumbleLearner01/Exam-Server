package com.rest.controller.user;

import com.rest.helper.payload.EmailRequest;
import com.rest.helper.payload.OTPRequest;
import com.rest.helper.payload.OTPResponse;
import com.rest.service.MailService;
import com.rest.config.jwt.JwtUtil;
import com.rest.helper.payload.JwtRequest;
import com.rest.helper.payload.JwtResponse;
import com.rest.config.jwt.userdetailsservice.UserDetailsServiceImpl;
import com.rest.model.user.User;
import com.rest.repository.user.UserRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Data
@RestController
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;
    private final MailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private OTPResponse responseOTP = new OTPResponse();
    private String toEmail;

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("<<<<<<<<<< AuthenticationController -> authenticate: DISABLED USER " + e.getMessage() + " >>>>>>>>>>");
        } catch (BadCredentialsException e) {
            throw new Exception("<<<<<<<<<< AuthenticationController -> authenticate: BAD CREDENTIALS " + e.getMessage() + " >>>>>>>>>>");
        }
    }

    //generate the token
    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("<<<<<<<<<< AuthenticationController -> generateToken: USER NOT FOUND >>>>>>>>>>");
        }
        //if catch doesn't get executed, that means user is authenticated
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    //get the logged-in user
    @GetMapping("/current-user")
    public User currentUser(Principal principal) {
        return (User) userDetailsService.loadUserByUsername(principal.getName());
    }

    /*//////////////////////////////////////////////////////*//** @Forgot-password-implementation **//*//////////////////////////////////////////////////////*/
    /*generate otp*/
    @PostMapping("/forgot-pass/send-otp")
    public @ResponseBody ResponseEntity<?> generateOTP(@RequestBody EmailRequest emailRequest) {
        toEmail = emailRequest.getTo();
        boolean email = emailService.sendEmail(emailRequest.getTo(), emailRequest.getMessage() + responseOTP, emailRequest.getSubject());
        if (email) {
            return ResponseEntity.ok(responseOTP);
        } else return ResponseEntity.ok("Email not sent :(");
    }

    /*verify the otp that we sent to the user*/
    @PostMapping("/forgot-pass/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody OTPRequest request) {
        if (request.getOtpRequest().equals(responseOTP.getOtp())) {
            User user = userRepo.findByEmail(toEmail);
            if (user != null) {
                return ResponseEntity.ok(toEmail);
            } else {
                return ResponseEntity.ok("user not found");
            }
        } else
            return new ResponseEntity<>("<<<<<<<<<< OTP does not match! >>>>>>>>>>", HttpStatus.NOT_ACCEPTABLE);
    }

    /*change the password*/
    @PostMapping("/forgot-pass/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody User user) {
        User u = userRepo.findByEmail(toEmail);
        u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(u);
        return ResponseEntity.ok("ok>>>>>>>    " + user);
    }

    //TODO: have to implement otp expiring in a limited time later
    private static final long OTP_VALID_DURATION = 1 * 60 * 1000;   // 1 minutes
    public boolean isOTPRequired(OTPRequest request) {
        if (Objects.equals(request.getOtpRequest(), "")) {
            return false;
        }
        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeMillis = request.getOtpRequestedTime().getTime();
        if (otpRequestedTimeMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            // otp expires
            return false;
        }
        return true;
    }
}