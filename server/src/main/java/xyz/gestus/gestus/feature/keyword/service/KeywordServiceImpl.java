package xyz.gestus.gestus.feature.keyword.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.feature.keyword.dto.KeywordRequest;
import xyz.gestus.gestus.feature.keyword.dto.KeywordResponse;
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
    public KeywordResponse createKeyword(KeywordRequest requestDto) {
        Keyword newKeyword = new Keyword();
        newKeyword.setName(requestDto.getName());

        Keyword createdKeyword = keywordRepository.save(newKeyword);

        return mapEntityToResponse(createdKeyword);
    }

    private KeywordResponse mapEntityToResponse(Keyword keywordModel){
        KeywordResponse response = new KeywordResponse();
        response.setId(keywordModel.getId());
        response.setName(keywordModel.getName());

        return response;
    }



}
