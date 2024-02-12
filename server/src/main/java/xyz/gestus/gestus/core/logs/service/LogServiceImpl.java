package xyz.gestus.gestus.core.logs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.core.logs.dto.LogResponseDto;
import xyz.gestus.gestus.core.logs.Log;
import xyz.gestus.gestus.core.logs.LogRepository;

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
        List<Log> logModels = logRepository.getLogModelByUserId(userId);

        List<LogResponseDto> response = new ArrayList<>();
        for(Log model :logModels){
            LogResponseDto responseDto = new LogResponseDto();
            responseDto.setDate(model.getDate());
            responseDto.setName(model.getName());

            response.add(responseDto);
        }

        return response;
    }
}
