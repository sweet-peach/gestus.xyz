package xyz.gestus.gestus.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.gestus.gestus.dto.KeywordRequestDto;
import xyz.gestus.gestus.dto.KeywordResponseDto;
import xyz.gestus.gestus.services.KeywordService;

@RestController
@RequestMapping("/api/keywords")
public class KeywordsController {

    KeywordService keywordService;

    @Autowired
    public KeywordsController(KeywordService keywordService){
        this.keywordService = keywordService;
    }

    @PostMapping
    public ResponseEntity<KeywordResponseDto> createKeyword(@Valid @RequestBody KeywordRequestDto requestDto){
        return new ResponseEntity<>(keywordService.createKeyword(requestDto), HttpStatus.OK);
    }

}
