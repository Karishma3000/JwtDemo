package com.example.UserService.Entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class JwtResponse {
    private String jwtToken;
    private  String username;
}
