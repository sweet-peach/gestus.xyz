package xyz.gestus.gestus.services;

import xyz.gestus.gestus.dto.*;
import xyz.gestus.gestus.models.UserModel;

import java.util.List;

public interface UserService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    void register(RegistrationRequestDto registrationRequestDto);
    UserResponseDto getUserByEmail(String email);

    List<UserResponseDto> getUsers();

    UserResponseDto updateUser(Long userId, UserUpdateDto updateDto);

    void deleteUser(Long userId);
}
