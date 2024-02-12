package xyz.gestus.gestus.feature.auth.dto;

import lombok.Data;
import xyz.gestus.gestus.core.user.dto.UserResponseDto;

@Data
public class LoginResponseDto {
    private UserResponseDto user;
    private String token;
    private String tokenType = "Bearer";
}
