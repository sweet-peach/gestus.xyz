package xyz.gestus.gestus.feature.keyword.service;

import xyz.gestus.gestus.feature.keyword.dto.KeywordRequestDto;
import xyz.gestus.gestus.feature.keyword.dto.KeywordResponseDto;

public interface KeywordService {
    public KeywordResponseDto createKeyword(KeywordRequestDto requestDto);
}
