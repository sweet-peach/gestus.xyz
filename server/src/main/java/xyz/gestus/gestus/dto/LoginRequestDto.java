package xyz.gestus.gestus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequestDto {
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
