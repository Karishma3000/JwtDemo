package com.example.UserService.Controller;

import com.example.UserService.Entity.JwtRequest;
import com.example.UserService.Entity.JwtResponse;
import com.example.UserService.Entity.Password;
import com.example.UserService.Payload.PasswordDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface PasswordController {
    @PostMapping("/singUp")
    public ResponseEntity<String> signUp(@RequestBody Password password);
   /* @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password);
   */ @PostMapping("/twostepverification")
    public ResponseEntity<String> otpVerficationLogin(@RequestParam String username,@RequestParam String otp);
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam String username,@RequestParam String oldPassword,@RequestParam String newPassword);
    @PostMapping("/loginJwt")
    public ResponseEntity<JwtResponse> loginJwt(@RequestBody JwtRequest request);
    @PostMapping("/admincanAccesss")
    public ResponseEntity<String> Admin();
    @PostMapping("/supperadmincanAccesss")
    public ResponseEntity<String> SuperAdmin();
    @PostMapping("/staffadmincanAccesss")
    public ResponseEntity<String> staff();
    @PostMapping("/manageradmincanAccesss")
    public ResponseEntity<String> manager();
    @PostMapping("/usercanAccess")
    public ResponseEntity<String> User();

    @PostMapping("/signupverification")
    public ResponseEntity<String> otpVerficationSingup(@RequestParam String username,@RequestParam String otp);
   /* @PostMapping("/forgetPassword")
    public String otpVerficationSingup(@RequestParam String username,@RequestParam String otp);*/
}
