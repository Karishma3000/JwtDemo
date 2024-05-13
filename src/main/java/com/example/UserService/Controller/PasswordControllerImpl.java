package com.example.UserService.Controller;

import com.example.UserService.Entity.JwtRequest;
import com.example.UserService.Entity.JwtResponse;
import com.example.UserService.Entity.Password;
import com.example.UserService.Payload.PasswordDto;
import com.example.UserService.Repository.PasswordRepo;
import com.example.UserService.ServiceImpl.PasswordServiceImpl;
import com.example.UserService.security.JwtHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/userAuth")
public class PasswordControllerImpl implements PasswordController {
    @Autowired
    AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;
    @Autowired
    private PasswordRepo passwordRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordServiceImpl passwordServiceImpl;
    Base64.Encoder encoder = Base64.getMimeEncoder();

    @Override
    public ResponseEntity<String> signUp(@RequestBody Password password) {
        try {
        if(!(password.getPassword()==null && password.getUsername()==null)){
            if(!(Objects.equals(password.getPassword(), "") && Objects.equals(password.getUsername(), ""))){
                String serviceReturn =passwordServiceImpl.signUp(password);
                return new ResponseEntity<>(serviceReturn,HttpStatus.OK);
            }else {
                return new ResponseEntity<>("username and password invalid",HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>("username and pass invalid",HttpStatus.BAD_REQUEST);
            }}catch (Exception e){
            return new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);

        }
    }

   /* @Override
    public ResponseEntity<String> login(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty()){
            if (!(username ==null && password ==null)){
                String serviceReturn =passwordServiceImpl.login(username, password);
                return new ResponseEntity<>(serviceReturn,HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>("username and password can not be null",HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>("username and password can not be empty",HttpStatus.BAD_REQUEST);
        }

    }*/
   @PostMapping("/loginJwt")
   public ResponseEntity<JwtResponse> loginJwt(@RequestBody JwtRequest request) {

       Optional<Password> existUser = passwordRepo.findByUsername(request.getEmail());

               this.doAuthenticate(request.getEmail(),request.getPassword());
             UserDetails  userDetails = userDetailsService.loadUserByUsername(request.getEmail());
               String token = this.helper.generateToken(userDetails);

       String otp=passwordServiceImpl.generateOTP();
       LocalDateTime currentTime=LocalDateTime.now();
       existUser.get().setCreationOtp(currentTime);
       existUser.get().setOtp(otp);
       passwordRepo.save(existUser.get());

               JwtResponse response = JwtResponse.builder()
                       .jwtToken(token)
                       .username(userDetails.getUsername())
                       .build();
               return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
           }


       private void doAuthenticate (String username, String password){
           UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
           try {
               manager.authenticate(authentication);
           } catch (BadCredentialsException e) {
               throw new RuntimeException("invalid Username Or Password  !!");
           }

       }
       @ExceptionHandler(BadCredentialsException.class)
       public String exceptionHandler () {
           return "Credentials Invalid !!";
       }

       @Override
       public ResponseEntity<String> otpVerficationLogin (String username, String otp){
           if (!otp.isEmpty() && !username.isEmpty()) {
               String serviceReturn = passwordServiceImpl.otpVerficationLogin(username, otp);
               return new ResponseEntity<>(serviceReturn, HttpStatus.OK);

           } else {
               return new ResponseEntity<>("please enter 6 digit of otp no sended on your email", HttpStatus.BAD_REQUEST);
           }
       }

       @Override
       public ResponseEntity<String> changePassword (String username, String oldPassword, String newPassword){
           if (!username.isEmpty() && !oldPassword.isEmpty() && !newPassword.isEmpty()) {
               if (!username.isBlank() && !oldPassword.isBlank() && !newPassword.isBlank()) {

                   String serviceReturn = passwordServiceImpl.changePassword(username, oldPassword, newPassword);
                   return new ResponseEntity<>(serviceReturn, HttpStatus.OK);

               } else {
                   return new ResponseEntity<>("user name, old pass, new pass cant be null", HttpStatus.BAD_REQUEST);
               }
           } else {
               return new ResponseEntity<>("user name, old pass, new pass cant be black", HttpStatus.BAD_REQUEST);
           }
       }

       @Override
       public ResponseEntity<String> otpVerficationSingup (String username, String otp){
           if (!otp.isEmpty() && !username.isEmpty()) {

               String serviceReturn = passwordServiceImpl.otpVerficationSignup(username, otp);
               return new ResponseEntity<>(serviceReturn, HttpStatus.OK);

           } else {
               return new ResponseEntity<>("please enter 6 digit of otp no sended on your email", HttpStatus.BAD_REQUEST);
           }
       }
    @Override
    public ResponseEntity<String> Admin() {

        return new ResponseEntity<>("welcome admin", HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<String> SuperAdmin() {
        return new ResponseEntity<>("welcome Super admin", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> staff() {
        return new ResponseEntity<>("welcome staff", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> manager() {
        return new ResponseEntity<>("welcome manager", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> User() {
        return new ResponseEntity<>("welcome User", HttpStatus.BAD_REQUEST);
    }

}
