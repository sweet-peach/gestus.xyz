package xyz.gestus.gestus.feature.statistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.feature.logs.LogRepository;
import xyz.gestus.gestus.feature.statistic.dto.UserActivityResponse;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImp implements StatisticService {

    LogRepository logRepository;

    @Autowired
    public StatisticServiceImp(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public List<UserActivityResponse> getUserActivityByYear(Long userId, int year) {
        List<Map<String, Object>> activityData = logRepository.findUserActivityByYear(userId, year);

        return activityData.stream()
                .map(entry -> {
                    Long count = (Long) entry.get("count");
                    Date sqlDate = (Date) entry.get("date");
                    Instant instant = new java.util.Date(sqlDate.getTime()).toInstant();
                    LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                    return new UserActivityResponse(localDateTime, count);
                })
                .collect(Collectors.toList());
    }
}
