package xyz.gestus.gestus.core.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.core.user.dto.UserResponseDto;
import xyz.gestus.gestus.core.user.dto.UserUpdateDto;
import xyz.gestus.gestus.feature.auth.dto.LoginRequestDto;
import xyz.gestus.gestus.feature.auth.dto.LoginResponseDto;
import xyz.gestus.gestus.feature.auth.dto.RegistrationRequestDto;
import xyz.gestus.gestus.core.user.UserRepository;
import xyz.gestus.gestus.core.user.User;
import xyz.gestus.gestus.core.user.Role;
import xyz.gestus.gestus.core.logs.LogRepository;
import xyz.gestus.gestus.core.security.JwtTokenProvider;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;

    private LogRepository logRepository;
    @Autowired
    public UserServiceImpl(LogRepository logRepository, JwtTokenProvider tokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.logRepository = logRepository;
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

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setToken(jwt);
        responseDto.setUser(mapEntityToResponse(user));

        return responseDto;
    }

    @Override
    public void register(RegistrationRequestDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new UsernameNotFoundException("A username associated with this email already exists");
        }

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Role.valueOf(registerDto.getRole()));

        userRepository.save(user);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapEntityToResponse(user);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapEntityToResponse(user);
    }

    @Override
    public List<UserResponseDto> getUsers() {
        List<User> userModels = userRepository.findAll();

        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    private UserResponseDto mapEntityToResponse(User user) {
        UserResponseDto response = new UserResponseDto();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().toString());
        return response;
    }

    public UserResponseDto updateUser(Long userId, UserUpdateDto updateDto) {
        return userRepository.findById(userId).map(user -> {
            updateDto.getFirstName().ifPresent(user::setFirstName);
            updateDto.getLastName().ifPresent(user::setLastName);
            updateDto.getEmail().ifPresent(user::setEmail);
            updateDto.getPassword().ifPresent(password -> user.setPassword(passwordEncoder.encode(password)));
            updateDto.getRole().ifPresent(user::setRole);

            User updatedUser = userRepository.save(user);
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

    @Override
    public List<Object[]> findTop5UsersWithMostLogs() {
        Pageable pageable = PageRequest.of(0, 5);
        return logRepository.findTopUsersWithMostLogs(pageable);
    }
}
