package xyz.gestus.gestus.feature.auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xyz.gestus.gestus.core.annotations.Log;
import xyz.gestus.gestus.feature.auth.dto.LoginResponse;
import xyz.gestus.gestus.feature.auth.dto.LoginRequest;
import xyz.gestus.gestus.feature.auth.dto.RegistrationRequest;
import xyz.gestus.gestus.feature.user.User;
import xyz.gestus.gestus.feature.user.dto.UserResponse;
import xyz.gestus.gestus.feature.user.service.UserServiceImpl;

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
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegistrationRequest registerRequestDto) {

        return new ResponseEntity<>(userService.register(registerRequestDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserResponse> authorize() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return new ResponseEntity<>(userService.getUserByEmail(userEmail), HttpStatus.OK);
    }

    @PostMapping("login")
    @Log(name = "User logged in")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody
                                                      LoginRequest loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }
}
