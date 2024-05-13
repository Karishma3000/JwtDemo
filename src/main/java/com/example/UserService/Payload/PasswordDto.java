package com.example.UserService.Payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
public class PasswordDto {

    private String username;


    private String password;



}
