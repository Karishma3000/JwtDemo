package com.example.UserService.Repository;

import com.example.UserService.Entity.Password;
import com.example.UserService.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PasswordRepo extends JpaRepository<Password, Long> {
    public Optional<Password> findByUsername(String username);




}
