package xyz.gestus.gestus.controllers;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.gestus.gestus.dto.LogResponseDto;
import xyz.gestus.gestus.dto.UserLogResponseDto;
import xyz.gestus.gestus.models.UserModel;
import xyz.gestus.gestus.services.LogService;
import xyz.gestus.gestus.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    LogService logService;
    UserService userService;

    @Autowired
    public LogController(LogService logService, UserService userService){
        this.logService = logService;
        this.userService = userService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<LogResponseDto>> getLogsByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(logService.getLogsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<UserLogResponseDto>> getTopUsersWithMostLogs() {
        List<Object[]> usersWithLogs = userService.findTop5UsersWithMostLogs();
        List<UserLogResponseDto> userLogDTOS = usersWithLogs.stream()
                .map(obj -> new UserLogResponseDto(((UserModel) obj[0]).getId(), ((UserModel) obj[0]).getEmail(), (Long) obj[1]))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userLogDTOS);
    }
}
