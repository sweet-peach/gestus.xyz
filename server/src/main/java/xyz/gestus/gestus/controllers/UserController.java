package xyz.gestus.gestus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.gestus.gestus.annotations.Log;
import xyz.gestus.gestus.dto.UserResponseDto;
import xyz.gestus.gestus.dto.UserUpdateDto;
import xyz.gestus.gestus.services.UserService;

import javax.swing.text.html.HTML;
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
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Log(name = "A user has updated the project")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto updateDto) {
        return new ResponseEntity<>(userService.updateUser(id, updateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Log(name = "A user has deleted the project")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
