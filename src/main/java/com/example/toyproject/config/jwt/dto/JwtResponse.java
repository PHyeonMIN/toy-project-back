package com.example.toyproject.config.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type ="Bearer";
    private Long id;
    private String username;
    private List<String> roles;

    @Builder
    public JwtResponse(String token, Long id, String username, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
