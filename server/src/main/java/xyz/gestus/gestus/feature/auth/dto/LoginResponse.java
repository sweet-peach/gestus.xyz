package xyz.gestus.gestus.feature.auth.dto;

import lombok.Data;
import xyz.gestus.gestus.feature.user.dto.UserResponse;

@Data
public class LoginResponse {
    private UserResponse user;
    private String token;
    private String tokenType = "Bearer";
}
