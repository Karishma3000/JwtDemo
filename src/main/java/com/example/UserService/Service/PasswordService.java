package com.example.UserService.Service;

import com.example.UserService.Entity.JwtRequest;
import com.example.UserService.Entity.JwtResponse;
import com.example.UserService.Entity.Password;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface PasswordService {
    String signUp(Password password);

    String login(String username, String password);

    String otpVerficationLogin(String username ,String otp);

    String changePassword(String username, String oldPassword, String newPassword);

    String otpVerficationSignup(String username, String otp);

}
