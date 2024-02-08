package xyz.gestus.gestus.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.dto.*;
import xyz.gestus.gestus.models.Role;
import xyz.gestus.gestus.models.UserModel;
import xyz.gestus.gestus.repositories.UserRepository;
import xyz.gestus.gestus.security.JwtTokenProvider;
import xyz.gestus.gestus.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public UserServiceImpl(JwtTokenProvider tokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        UserModel userModel = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setAccessToken(jwt);
        responseDto.setUserResponseDto(mapEntityToResponse(userModel));

        return responseDto;
    }

    @Override
    public void register(RegistrationRequestDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new UsernameNotFoundException("A username associated with this email already exists");
        }

        UserModel userModel = new UserModel();
        userModel.setFirstName(registerDto.getFirstName());
        userModel.setLastName(registerDto.getLastName());
        userModel.setEmail(registerDto.getEmail());
        userModel.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userModel.setRole(Role.valueOf(registerDto.getRole()));

        userRepository.save(userModel);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        UserModel user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapEntityToResponse(user);
    }

    @Override
    public List<UserResponseDto> getUsers() {
        List<UserModel> userModels = userRepository.findAll();

        List<UserModel> users = userRepository.findAll();
        return users.stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    private UserResponseDto mapEntityToResponse(UserModel userModel) {
        UserResponseDto response = new UserResponseDto();
        response.setId(userModel.getId());
        response.setFirstName(userModel.getFirstName());
        response.setLastName(userModel.getLastName());
        response.setEmail(userModel.getEmail());
        response.setRole(userModel.getRole().toString());
        return response;
    }

    public UserResponseDto updateUser(Long userId, UserUpdateDto updateDto) {
        return userRepository.findById(userId).map(user -> {
            updateDto.getFirstName().ifPresent(user::setFirstName);
            updateDto.getLastName().ifPresent(user::setLastName);
            updateDto.getEmail().ifPresent(user::setEmail);
            updateDto.getPassword().ifPresent(password -> user.setPassword(passwordEncoder.encode(password)));
            updateDto.getRole().ifPresent(user::setRole);

            UserModel updatedUser = userRepository.save(user);
            return mapEntityToResponse(updatedUser);
        }).orElseThrow(() -> new UsernameNotFoundException("User not found with id " + userId));
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UsernameNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
