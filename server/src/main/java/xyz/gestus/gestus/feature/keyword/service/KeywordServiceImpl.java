package xyz.gestus.gestus.feature.keyword.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.feature.keyword.dto.KeywordRequestDto;
import xyz.gestus.gestus.feature.keyword.dto.KeywordResponseDto;
import xyz.gestus.gestus.feature.keyword.Keyword;
import xyz.gestus.gestus.feature.keyword.KeywordRepository;

@Service
public class KeywordServiceImpl implements KeywordService {

    KeywordRepository keywordRepository;

    @Autowired
    public KeywordServiceImpl(KeywordRepository keywordRepository){
        this.keywordRepository = keywordRepository;
    }

    @Override
    public KeywordResponseDto createKeyword(KeywordRequestDto requestDto) {
        Keyword newKeyword = new Keyword();
        newKeyword.setName(requestDto.getName());

        Keyword createdKeyword = keywordRepository.save(newKeyword);

        return null;
    }

    private KeywordResponseDto mapEntityToResponse(Keyword keywordModel){
        KeywordResponseDto response = new KeywordResponseDto();
        response.setId(keywordModel.getId());
        response.setName(keywordModel.getName());

        return response;
    }



}
