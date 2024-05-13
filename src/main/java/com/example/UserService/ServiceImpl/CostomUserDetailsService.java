package com.example.UserService.ServiceImpl;

import com.example.UserService.Entity.Password;
import com.example.UserService.Repository.PasswordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CostomUserDetailsService implements  UserDetailsService {
  @Autowired
  private PasswordRepo passwordRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load user from database
        Password user=passwordRepo.findByUsername(username).orElseThrow(()->new RuntimeException("UserNot Found"));
        System.out.println(user);
        return user;
    }

}
