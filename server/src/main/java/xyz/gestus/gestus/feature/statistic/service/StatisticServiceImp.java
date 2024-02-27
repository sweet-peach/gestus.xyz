package xyz.gestus.gestus.feature.statistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.feature.file.File;
import xyz.gestus.gestus.feature.file.FileRepository;
import xyz.gestus.gestus.feature.logs.LogRepository;
import xyz.gestus.gestus.feature.statistic.dto.FileStatisticResponse;
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
    FileRepository fileRepository;

    @Autowired
    public StatisticServiceImp(LogRepository logRepository, FileRepository fileRepository) {
        this.logRepository = logRepository;
        this.fileRepository = fileRepository;
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

    @Override
    public List<FileStatisticResponse> getProjectExtensionsStatistic(Long projectId) {
        List<File> fileList = fileRepository.findByProjectId(projectId);

        return fileList.stream()
                .filter(file -> !file.getType().contains("dir"))
                .map(file -> file.getName().substring(file.getName().lastIndexOf(".") + 1)).collect(Collectors.groupingBy(extension -> extension, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new FileStatisticResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
