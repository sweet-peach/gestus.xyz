package xyz.gestus.gestus.feature.user.service;

import xyz.gestus.gestus.feature.user.dto.UserResponse;
import xyz.gestus.gestus.feature.user.dto.UserUpdateRequest;
import xyz.gestus.gestus.feature.auth.dto.LoginRequest;
import xyz.gestus.gestus.feature.auth.dto.LoginResponse;
import xyz.gestus.gestus.feature.auth.dto.RegistrationRequest;

import java.util.List;

public interface UserService {
    LoginResponse login(LoginRequest loginRequestDto);
    void register(RegistrationRequest registrationRequestDto);
    UserResponse getUserByEmail(String email);
    UserResponse getUserById(Long id);

    List<UserResponse> getUsers();

    UserResponse updateUser(Long userId, UserUpdateRequest updateDto);

    void deleteUser(Long userId);

    List<Object[]> findTop5UsersWithMostLogs();
}
