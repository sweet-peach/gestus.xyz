package xyz.gestus.gestus.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private UserResponseDto userResponseDto;
    private String accessToken;
    private String tokenType = "Bearer";
}
