package xyz.gestus.gestus.feature.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.feature.keyword.dto.KeywordResponse;
import xyz.gestus.gestus.feature.keyword.service.KeywordService;
import xyz.gestus.gestus.feature.project.dto.ProjectSearchResponse;
import xyz.gestus.gestus.feature.project.service.ProjectService;
import xyz.gestus.gestus.feature.search.dto.SearchResponse;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{

    ProjectService projectService;
    KeywordService keywordService;

    @Autowired
    public SearchServiceImpl(ProjectService projectService, KeywordService keywordService) {
        this.projectService = projectService;
        this.keywordService = keywordService;
    }

    @Override
    public SearchResponse search(String query, List<Long> keywords) {
        List<ProjectSearchResponse> projects = projectService.searchProjects(query, keywords, null);
        List<KeywordResponse> keywordResponses = keywordService.searchKeywords(query);

        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setProjects(projects);
        searchResponse.setKeywords(keywordResponses);

        return searchResponse;
    }
}
