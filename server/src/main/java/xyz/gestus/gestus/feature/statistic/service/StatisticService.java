package xyz.gestus.gestus.feature.statistic.service;

import xyz.gestus.gestus.feature.statistic.dto.FileStatisticResponse;
import xyz.gestus.gestus.feature.statistic.dto.UserActivityResponse;

import java.util.List;

public interface StatisticService {
    List<UserActivityResponse> getUserActivityByYear(Long userId, int year);
    List<FileStatisticResponse> getProjectExtensionsStatistic(Long projectId);
}
