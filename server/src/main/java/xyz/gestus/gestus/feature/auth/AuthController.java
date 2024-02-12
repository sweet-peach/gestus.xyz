package xyz.gestus.gestus.feature.auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xyz.gestus.gestus.annotations.Log;
import xyz.gestus.gestus.feature.auth.dto.LoginResponseDto;
import xyz.gestus.gestus.feature.auth.dto.LoginRequestDto;
import xyz.gestus.gestus.feature.auth.dto.RegistrationRequestDto;
import xyz.gestus.gestus.core.user.dto.UserResponseDto;
import xyz.gestus.gestus.core.user.service.UserServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserServiceImpl userService;

    @Autowired
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("registration")
    @Log(name = "A user has registered a new user")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationRequestDto registerRequestDto) {
        userService.register(registerRequestDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> authorize() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return new ResponseEntity<>(userService.getUserByEmail(userEmail), HttpStatus.OK);
    }

    @PostMapping("login")
    @Log(name = "User logged in")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody
                                                      LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }
}
