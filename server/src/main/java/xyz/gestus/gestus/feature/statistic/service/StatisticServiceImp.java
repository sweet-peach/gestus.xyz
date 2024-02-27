package xyz.gestus.gestus.feature.statistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.feature.file.File;
import xyz.gestus.gestus.feature.file.FileRepository;
import xyz.gestus.gestus.feature.logs.LogRepository;
import xyz.gestus.gestus.feature.statistic.dto.ExtensionCountResponse;
import xyz.gestus.gestus.feature.statistic.dto.UserActivityResponse;
import xyz.gestus.gestus.feature.statistic.dto.UserLogCountResponse;
import xyz.gestus.gestus.feature.user.User;

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
    public List<ExtensionCountResponse> getProjectExtensionsStatistic(Long projectId) {
        List<File> fileList = fileRepository.findByProjectId(projectId);

        return mapToProjectExtensionsStatistic(fileList);
    }

    @Override
    public List<ExtensionCountResponse> getProjectsExtensionsStatistic() {
        List<File> allFiles = fileRepository.findAll();

        return mapToProjectExtensionsStatistic(allFiles);
    }

    @Override
    public List<UserLogCountResponse> getUsersWithMostLogs(int quantity) {
        Pageable pageable = PageRequest.of(0, quantity);
        List<Object[]> usersWithLogs = logRepository.findTopUsersWithMostLogs(pageable);
        return usersWithLogs.stream()
                .map(obj -> new UserLogCountResponse(((User) obj[0]).getId(), ((User) obj[0]).getEmail(), (Long) obj[1]))
                .collect(Collectors.toList());
    }

    private List<ExtensionCountResponse> mapToProjectExtensionsStatistic(List<File> fileList) {
        return fileList.stream()
                .filter(file -> !file.getType().contains("dir"))
                .map(file -> file.getName().substring(file.getName().lastIndexOf(".") + 1)).collect(Collectors.groupingBy(extension -> extension, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new ExtensionCountResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
