package com.example.UserService.Entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.aspectj.bridge.IMessage;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.access.method.P;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Getter
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "password")
@Entity
public class Password implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Email
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "otp")
    private String otp;

    @Column(name = "role")
    private  Role role;


    @Column(name = "creationOtp")
    private LocalDateTime creationOtp;

    @Column(name="Verification")
    private Boolean verification;

    @Column(name="VerificationAttempts")
    private Boolean VerificationAttempts;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
