package xyz.gestus.gestus.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private UserResponseDto user;
    private String token;
    private String tokenType = "Bearer";
}
