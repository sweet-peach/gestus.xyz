package xyz.gestus.gestus.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.dto.LogResponseDto;
import xyz.gestus.gestus.models.LogModel;
import xyz.gestus.gestus.repositories.LogRepository;
import xyz.gestus.gestus.services.LogService;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    private LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository){
        this.logRepository = logRepository;
    }

    @Override
    public List<LogResponseDto> getLogsByUserId(Long userId) {
        List<LogModel> logModels = logRepository.getLogModelByUserId(userId);

        List<LogResponseDto> response = new ArrayList<>();
        for(LogModel model :logModels){
            LogResponseDto responseDto = new LogResponseDto();
            responseDto.setDate(model.getDate());
            responseDto.setName(model.getName());

            response.add(responseDto);
        }

        return response;
    }
}
