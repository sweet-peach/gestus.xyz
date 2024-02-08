package xyz.gestus.gestus.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.dto.KeywordRequestDto;
import xyz.gestus.gestus.dto.KeywordResponseDto;
import xyz.gestus.gestus.models.KeywordModel;
import xyz.gestus.gestus.repositories.KeywordRepository;
import xyz.gestus.gestus.services.KeywordService;

@Service
public class KeywordServiceImpl implements KeywordService {

    KeywordRepository keywordRepository;

    @Autowired
    public KeywordServiceImpl(KeywordRepository keywordRepository){
        this.keywordRepository = keywordRepository;
    }

    @Override
    public KeywordResponseDto createKeyword(KeywordRequestDto requestDto) {
        KeywordModel newKeyword = new KeywordModel();
        newKeyword.setName(requestDto.getName());

        KeywordModel createdKeyword = keywordRepository.save(newKeyword);

        return null;
    }

    private KeywordResponseDto mapEntityToResponse(KeywordModel keywordModel){
        KeywordResponseDto response = new KeywordResponseDto();
        response.setId(keywordModel.getId());
        response.setName(keywordModel.getName());

        return response;
    }



}
