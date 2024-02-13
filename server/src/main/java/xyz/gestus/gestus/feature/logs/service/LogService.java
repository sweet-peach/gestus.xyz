package xyz.gestus.gestus.feature.logs.service;

import xyz.gestus.gestus.feature.logs.dto.LogResponse;

import java.util.List;

public interface LogService {
    public List<LogResponse> getLogsByUserId(Long userId);
}
