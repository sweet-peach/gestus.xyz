package xyz.gestus.gestus.feature.keyword;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.gestus.gestus.core.annotations.Log;
import xyz.gestus.gestus.feature.keyword.dto.KeywordRequest;
import xyz.gestus.gestus.feature.keyword.dto.KeywordResponse;
import xyz.gestus.gestus.feature.keyword.service.KeywordService;

@RestController
@RequestMapping("/api/keywords")
public class KeywordsController {

    KeywordService keywordService;

    @Autowired
    public KeywordsController(KeywordService keywordService){
        this.keywordService = keywordService;
    }

    @PostMapping
    @Log(name = "A user has created a keyword")
    public ResponseEntity<KeywordResponse> createKeyword(@Valid @RequestBody KeywordRequest requestDto){
        return new ResponseEntity<>(keywordService.createKeyword(requestDto), HttpStatus.OK);
    }

}
