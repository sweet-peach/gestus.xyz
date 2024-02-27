package xyz.gestus.gestus.feature.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.feature.user.dto.UserResponse;
import xyz.gestus.gestus.feature.user.dto.UserUpdateRequest;
import xyz.gestus.gestus.feature.auth.dto.LoginRequest;
import xyz.gestus.gestus.feature.auth.dto.LoginResponse;
import xyz.gestus.gestus.feature.auth.dto.RegistrationRequest;
import xyz.gestus.gestus.feature.user.UserRepository;
import xyz.gestus.gestus.feature.user.User;
import xyz.gestus.gestus.feature.user.Role;
import xyz.gestus.gestus.feature.logs.LogRepository;
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
    public LoginResponse login(LoginRequest loginRequestDto) {
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
        LoginResponse responseDto = new LoginResponse();
        responseDto.setToken(jwt);
        responseDto.setUser(mapEntityToResponse(user));

        return responseDto;
    }

    @Override
    public UserResponse register(RegistrationRequest registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new UsernameNotFoundException("A username associated with this email already exists");
        }

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Role.valueOf(registerDto.getRole()));

        User createdUser =  userRepository.save(user);
        return mapEntityToResponse(createdUser);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapEntityToResponse(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapEntityToResponse(user);
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getUsers(String sortBy, String sortDirection) {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

        return users.stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapEntityToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().toString());
        return response;
    }

    public UserResponse updateUser(Long userId, UserUpdateRequest updateDto) {
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
}
