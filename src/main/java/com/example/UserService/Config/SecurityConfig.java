package com.example.UserService.Config;

import com.example.UserService.Entity.Password;
import com.example.UserService.Entity.Role;
import com.example.UserService.security.JwtAuthenticationEntryPoint;
import com.example.UserService.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import static org.springframework.security.config.Customizer.withDefaults;

@Component
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
//this is the end point
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //configuration
        http.csrf(csrf->csrf.disable())
                .cors(cors->cors.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/userAuth/singUp").permitAll()
                        .requestMatchers("/userAuth/login").permitAll()
                        .requestMatchers("/userAuth/loginJwt").permitAll()
                        .requestMatchers("/userAuth/admincanAccesss").hasAnyAuthority(com.example.UserService.Entity.Role.ADMIN.name())
                        .requestMatchers("/userAuth/supperadmincanAccesss").hasAnyAuthority(com.example.UserService.Entity.Role.SUPERADMIN.name())
                        .requestMatchers("/userAuth/staffadmincanAccesss").hasAnyAuthority(com.example.UserService.Entity.Role.MANAGER.name())
                        .requestMatchers("/userAuth/manageradmincanAccesss").hasAnyAuthority(com.example.UserService.Entity.Role.MANAGER.name())
                        .requestMatchers("/userAuth/usercanAccess").hasAnyAuthority(Role.USER.name())
                        .requestMatchers("/userAuth/signupverification").permitAll()
                        .requestMatchers("/userAuth/twostepverification").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(ex->ex.authenticationEntryPoint(point))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
       http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
/*@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    return http
            .authorizeHttpRequests(outh->{
                outh.requestMatchers("/user/**").authenticated();
                outh.requestMatchers("/userAuth/singUp").permitAll();
                outh.requestMatchers("/userAuth/login").permitAll();
                outh.requestMatchers("/userAuth/loginJwt").permitAll();
                outh.requestMatchers("/userAuth/signupverification").permitAll();
                outh.requestMatchers("/userAuth/twostepverification").permitAll();
                outh.anyRequest().authenticated();
            })
            .oauth2Login(withDefaults())
            .formLogin(withDefaults())
            .build();
}*/
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
       DaoAuthenticationProvider Provider=new DaoAuthenticationProvider();
       Provider.setUserDetailsService(userDetailsService);
       Provider.setPasswordEncoder(passwordEncoder);
        return Provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

}
