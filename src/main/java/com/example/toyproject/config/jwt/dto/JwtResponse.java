package com.example.toyproject.config.jwt.dto;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type ="Bearer";
    private Long id;
    private String username;
    private List<String> roles;
}
