package xyz.gestus.gestus.feature.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.gestus.gestus.feature.logs.dto.LogResponse;
import xyz.gestus.gestus.feature.logs.dto.UserLogResponse;
import xyz.gestus.gestus.feature.user.User;
import xyz.gestus.gestus.feature.logs.service.LogService;
import xyz.gestus.gestus.feature.user.service.UserService;

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
    public ResponseEntity<List<LogResponse>> getLogsByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(logService.getLogsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserLogResponse>> getTopUsersWithMostLogs() {
        List<Object[]> usersWithLogs = userService.findTop5UsersWithMostLogs();
        List<UserLogResponse> userLogDTOS = usersWithLogs.stream()
                .map(obj -> new UserLogResponse(((User) obj[0]).getId(), ((User) obj[0]).getEmail(), (Long) obj[1]))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userLogDTOS);
    }
}
