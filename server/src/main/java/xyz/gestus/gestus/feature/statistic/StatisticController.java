package xyz.gestus.gestus.feature.statistic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.gestus.gestus.feature.statistic.dto.ExtensionCountResponse;
import xyz.gestus.gestus.feature.statistic.dto.UserActivityResponse;
import xyz.gestus.gestus.feature.statistic.dto.UserLogCountResponse;
import xyz.gestus.gestus.feature.statistic.service.StatisticService;

import java.util.List;

@RestController
@RequestMapping("api/statistic")
public class StatisticController {

    StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ExtensionCountResponse>> getProjectsExtensions() {
        int quantity = 5;
        return new ResponseEntity<>(statisticService.getProjectsExtensionsStatistic(), HttpStatus.OK);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<ExtensionCountResponse>> getProjectExtensions(@PathVariable Long projectId) {
        return new ResponseEntity<>(statisticService.getProjectExtensionsStatistic(projectId), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserLogCountResponse>> getUsersLogCount() {
        int quantity = 5;
        return new ResponseEntity<>(statisticService.getUsersWithMostLogs(quantity), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/activity")
    public ResponseEntity<List<UserActivityResponse>> getUserActivityByYear(@PathVariable Long userId, @RequestParam(name = "year", required = false, defaultValue = "2024") int year) {
        return new ResponseEntity<>(statisticService.getUserActivityByYear(userId, year), HttpStatus.OK);
    }
}
