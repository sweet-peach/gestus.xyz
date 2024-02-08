package xyz.gestus.gestus.services;

import xyz.gestus.gestus.dto.LoginRequestDto;
import xyz.gestus.gestus.dto.LoginResponseDto;
import xyz.gestus.gestus.dto.RegistrationRequestDto;

public interface UserService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    void register(RegistrationRequestDto registrationRequestDto);
}
