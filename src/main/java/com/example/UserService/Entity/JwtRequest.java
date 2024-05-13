package com.example.UserService.Entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class JwtRequest {
        private String email;
        private String password;
    }

