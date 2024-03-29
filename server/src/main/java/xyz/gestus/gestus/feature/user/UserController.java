package xyz.gestus.gestus.feature.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.gestus.gestus.core.annotations.Log;
import xyz.gestus.gestus.feature.user.dto.UserResponse;
import xyz.gestus.gestus.feature.user.dto.UserUpdateRequest;
import xyz.gestus.gestus.feature.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

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
    @Log(name = "A user has updated the project")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest updateDto) {
        return new ResponseEntity<>(userService.updateUser(id, updateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Log(name = "A user has deleted the project")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
