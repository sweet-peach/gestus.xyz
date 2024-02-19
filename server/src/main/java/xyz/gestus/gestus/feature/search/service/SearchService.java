package xyz.gestus.gestus.feature.search.service;

import xyz.gestus.gestus.feature.project.dto.ProjectSearchResponse;
import xyz.gestus.gestus.feature.search.dto.SearchResponse;

import java.util.List;

public interface SearchService {
    SearchResponse search(String query, List<Long> keywords);

}
