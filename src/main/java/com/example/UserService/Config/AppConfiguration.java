package com.example.UserService.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfiguration {

   /* @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user=User.builder().username("karishma").password(passwordEncoder().encode("karishma")).roles("admin").build();
        UserDetails user1=User.builder().username("karish").password(passwordEncoder().encode("karish")).roles("admin").build();

        return new InMemoryUserDetailsManager(user,user1);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
