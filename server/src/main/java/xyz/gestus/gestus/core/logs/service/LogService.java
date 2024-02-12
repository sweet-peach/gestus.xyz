package xyz.gestus.gestus.core.logs.service;

import xyz.gestus.gestus.core.logs.dto.LogResponseDto;

import java.util.List;

public interface LogService {
    public List<LogResponseDto> getLogsByUserId(Long userId);
}
