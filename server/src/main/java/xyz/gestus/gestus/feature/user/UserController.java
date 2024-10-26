package xyz.gestus.gestus.feature.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xyz.gestus.gestus.core.annotations.Log;
import xyz.gestus.gestus.feature.user.dto.UserResponse;
import xyz.gestus.gestus.feature.user.dto.UserUpdateRequest;
import xyz.gestus.gestus.feature.user.exception.CannotModifyUserRoleException;
import xyz.gestus.gestus.feature.user.service.UserService;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("api/users")
public class UserController {

    @Value("${ROOT_USER_EMAIL}")
    private String rootUserEmail;

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(@RequestParam(name = "sortBy", required = false, defaultValue = "firstName") String sortBy,
                                                       @RequestParam(name = "sortDirection", required = false, defaultValue = "ASC") String sortDirection) {
        return new ResponseEntity<>(userService.getUsers(sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Log(name = "A user has updated a user")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest updateDto) {
        UserResponse rootUser = userService.getUserByEmail(rootUserEmail);
        Role newRole = updateDto.getRole().orElse(null);
        if(rootUser.getId().equals(id) && (newRole != null && !newRole.equals(Role.ADMIN))){
            throw new CannotModifyUserRoleException("You cannot modify role of root user");
        }
        return new ResponseEntity<>(userService.updateUser(id, updateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Log(name = "A user has deleted a user")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String callerEmail = authentication.getName();
        UserResponse callerUser = userService.getUserByEmail(callerEmail);

        if(Objects.equals(callerUser.getId(), id)) {
            return new ResponseEntity<>("You cannot delete yourself", HttpStatus.FORBIDDEN);
        }

        UserResponse rootUser = userService.getUserByEmail(rootUserEmail);
        if(rootUser.getId().equals(id)){
            return new ResponseEntity<>("You cannot delete root user", HttpStatus.FORBIDDEN);
        }

        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
