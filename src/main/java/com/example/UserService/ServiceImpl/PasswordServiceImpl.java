package com.example.UserService.ServiceImpl;

import com.example.UserService.Entity.JwtRequest;
import com.example.UserService.Entity.JwtResponse;
import com.example.UserService.Entity.Password;
import com.example.UserService.Repository.PasswordRepo;
import com.example.UserService.Service.PasswordService;
import com.example.UserService.security.JwtHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordServiceImpl implements PasswordService  {
    @Autowired
    private PasswordRepo passwordRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
     private JavaMailSender javaMailSender;
    Base64.Encoder encoder = Base64.getMimeEncoder();
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserSeviceImpl.class);

    @Override
    public String signUp(Password password) {
        Optional<Password> existUser = passwordRepo.findByUsername(password.getUsername());
        if (existUser.isPresent()) {
            logger.warn("User already exist");
            return "User already exist";
        } else {
            password.setPassword(passwordEncoder.encode(password.getPassword()));

         //   password.setPassword(encoder.encodeToString(password.getPassword().getBytes()));
            String otp =generateOTP();
           password.setOtp(otp);
            sendEmail(password.getUsername(),"OTP verification mail",otp);
            password.setCreationOtp(LocalDateTime.now());
            password.setVerification(false);
            passwordRepo.save(password);
            logger.info("User save Successfully");
            return "User save Successfully";
        }
    }
public String generateOTP(){
    Random random=new Random();
    int otpValue= 1000000+ random.nextInt(900000);
    return  String.valueOf(otpValue);
}
    @Override
    public String login(String username, String password) {
        Optional<Password> existUser = passwordRepo.findByUsername(username);
        if (existUser.isPresent()) {
            if (existUser.get().getVerification()){
            if (Objects.equals(existUser.get().getPassword(),encoder.encodeToString(password.getBytes()))){
                logger.info("your password is correct we will send otp on your email for 2 step verification");
               String otp=generateOTP();
               LocalDateTime currentTime=LocalDateTime.now();
               existUser.get().setCreationOtp(currentTime);
               existUser.get().setOtp(otp);
               passwordRepo.save(existUser.get());
               sendEmail(username,"OTP verification mail",otp);
                logger.info(otp);
                return "your password is correct we will send otp on your email for 2 step verification";
            }}else {
                return "plase do singup verification first";
            }
        }else {
            logger.warn("please singup first");
            return "please singup first";
        }
        return "something went wrong please try again";
    }

    @Override
    public String otpVerficationLogin(String username,String otp) {
        Optional<Password> existUser = passwordRepo.findByUsername(username);
        if (existUser.isPresent()) {
            if (existUser.get().getCreationOtp().isBefore(LocalDateTime.now()) && existUser.get().getCreationOtp().plusMinutes(5).isAfter(LocalDateTime.now())){
            if (Objects.equals(existUser.get().getOtp(), otp)){
                return "loging successfully";
            }else {
                return "otp is invalid";
            }
            }else {
                return "your tym for otp verification is out please login again";
            }}else {
            return "something went wrong please try again";
        }
    }

    @Override
    public String changePassword(String username, String oldPassword, String newPassword) {
        if(!(username==null && oldPassword==null && newPassword==null)) {
        Optional<Password> existUser = passwordRepo.findByUsername(username);
        if (existUser.isPresent()) {
            if (Objects.equals(existUser.get().getPassword(), encoder.encodeToString(newPassword.getBytes()))){
                return "you cant keep password same as old password";
            }
            if (Objects.equals(existUser.get().getPassword(), encoder.encodeToString(oldPassword.getBytes()))){
                existUser.get().setPassword(encoder.encodeToString(newPassword.getBytes()));
                passwordRepo.save(existUser.get());
                return "password change successffully";
            }
        }else {
            return "user not valid";
        }}else {
                return "user name, old pass, new pass cant be empty";
            }
        return "something went wrong";
    }

    @Override
    public String otpVerficationSignup(String username, String otp) {
        Optional<Password> existUser = passwordRepo.findByUsername(username);
        if (existUser.isPresent()) {
            if (existUser.get().getCreationOtp().isBefore(LocalDateTime.now()) && existUser.get().getCreationOtp().plusMinutes(5).isAfter(LocalDateTime.now())){
                if (Objects.equals(existUser.get().getOtp(), otp)){
                    existUser.get().setVerification(true);
                    passwordRepo.save(existUser.get());
                    return "Verification successfully";
                }else {
                    return "otp is invalid";
                }
            }else {
                return "your tym for otp verification is out please login again";
            }}else {
            return "something went wrong please try again";
        }
    }




    public void  sendEmail(String toEmail,
                           String subject,
                           String body){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setFrom("karishma.choudhary@intelliatech.com");
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(subject);
        javaMailSender.send(simpleMailMessage);
    }




  /*  private  Password dtoToUser(PasswordDto passwordDto) {
        Password password = this.modelMapper.map(passwordDto,Password.class);
        return password;
    }
    private  PasswordDto userToDto(Password password) {
        PasswordDto passwordDto = this.modelMapper.map(password,PasswordDto.class);
        return passwordDto;
    }*/
}
