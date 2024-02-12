package xyz.gestus.gestus.core.user.service;

import xyz.gestus.gestus.core.user.dto.UserResponseDto;
import xyz.gestus.gestus.core.user.dto.UserUpdateDto;
import xyz.gestus.gestus.feature.auth.dto.LoginRequestDto;
import xyz.gestus.gestus.feature.auth.dto.LoginResponseDto;
import xyz.gestus.gestus.feature.auth.dto.RegistrationRequestDto;

import java.util.List;

public interface UserService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    void register(RegistrationRequestDto registrationRequestDto);
    UserResponseDto getUserByEmail(String email);
    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getUsers();

    UserResponseDto updateUser(Long userId, UserUpdateDto updateDto);

    void deleteUser(Long userId);

    List<Object[]> findTop5UsersWithMostLogs();
}
