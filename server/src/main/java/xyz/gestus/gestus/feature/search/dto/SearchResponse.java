package xyz.gestus.gestus.feature.search.dto;

import lombok.Data;
import xyz.gestus.gestus.feature.keyword.dto.KeywordResponse;
import xyz.gestus.gestus.feature.project.dto.ProjectSearchResponse;

import java.util.List;

@Data
public class SearchResponse {
    private List<ProjectSearchResponse> projects;
    private List<KeywordResponse> keywords;
}
