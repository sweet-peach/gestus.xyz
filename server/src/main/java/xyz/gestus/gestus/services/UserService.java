package xyz.gestus.gestus.services;

import xyz.gestus.gestus.dto.LoginRequestDto;
import xyz.gestus.gestus.dto.LoginResponseDto;
import xyz.gestus.gestus.dto.RegistrationRequestDto;
import xyz.gestus.gestus.dto.UserResponseDto;

public interface UserService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    void register(RegistrationRequestDto registrationRequestDto);
    UserResponseDto getUserByEmail(String email);
}
