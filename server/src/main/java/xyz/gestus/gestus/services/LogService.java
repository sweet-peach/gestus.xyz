package xyz.gestus.gestus.services;

import xyz.gestus.gestus.dto.KeywordRequestDto;
import xyz.gestus.gestus.dto.KeywordResponseDto;
import xyz.gestus.gestus.dto.LogResponseDto;

import java.util.List;

public interface LogService {
    public List<LogResponseDto> getLogsByUserId(Long userId);
}
