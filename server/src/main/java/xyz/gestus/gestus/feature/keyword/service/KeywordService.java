package xyz.gestus.gestus.feature.keyword.service;

import xyz.gestus.gestus.feature.keyword.dto.KeywordRequest;
import xyz.gestus.gestus.feature.keyword.dto.KeywordResponse;

import java.util.List;

public interface KeywordService {
    public KeywordResponse createKeyword(KeywordRequest requestDto);
    public List<KeywordResponse> searchKeywords(String query);
}
