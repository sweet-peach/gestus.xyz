package xyz.gestus.gestus.feature.keyword.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.feature.keyword.dto.KeywordRequest;
import xyz.gestus.gestus.feature.keyword.dto.KeywordResponse;
import xyz.gestus.gestus.feature.keyword.Keyword;
import xyz.gestus.gestus.feature.keyword.KeywordRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<KeywordResponse> searchKeywords(String query) {
        Pageable limit = PageRequest.of(0, 5);

        List<Keyword> keywords = keywordRepository.findByNameContainingIgnoreCase(query,limit);

        return mapEntitiesToResponse(keywords);
    }

    private KeywordResponse mapEntityToResponse(Keyword keywordModel){
        KeywordResponse response = new KeywordResponse();
        response.setId(keywordModel.getId());
        response.setName(keywordModel.getName());

        return response;
    }

    private List<KeywordResponse> mapEntitiesToResponse(List<Keyword> keywordModels){
        return keywordModels.stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }


}
