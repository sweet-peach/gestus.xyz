package xyz.gestus.gestus.services;

import xyz.gestus.gestus.dto.KeywordRequestDto;
import xyz.gestus.gestus.dto.KeywordResponseDto;

public interface KeywordService {
    public KeywordResponseDto createKeyword(KeywordRequestDto requestDto);
}
