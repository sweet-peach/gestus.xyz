package xyz.gestus.gestus.feature.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.gestus.gestus.feature.project.dto.ProjectResponse;
import xyz.gestus.gestus.feature.search.dto.SearchResponse;
import xyz.gestus.gestus.feature.search.service.SearchService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<SearchResponse> search(@RequestParam(name = "query", required = false, defaultValue = "") String query,
                                                 @RequestParam(name = "keywords", required = false, defaultValue = "") List<Long> keywords) {



        return new ResponseEntity<>(searchService.search(query,keywords), HttpStatus.OK);
    }
}
