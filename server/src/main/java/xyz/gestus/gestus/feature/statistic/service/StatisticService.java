package xyz.gestus.gestus.feature.statistic.service;

import xyz.gestus.gestus.feature.statistic.dto.ExtensionCountResponse;
import xyz.gestus.gestus.feature.statistic.dto.UserActivityResponse;
import xyz.gestus.gestus.feature.statistic.dto.UserLogCountResponse;

import java.util.List;

public interface StatisticService {
    List<UserActivityResponse> getUserActivityByYear(Long userId, int year);
    List<ExtensionCountResponse> getProjectExtensionsStatistic(Long projectId);
    List<ExtensionCountResponse> getProjectsExtensionsStatistic();
    List<UserLogCountResponse> getUsersWithMostLogs(int quantity);
}
