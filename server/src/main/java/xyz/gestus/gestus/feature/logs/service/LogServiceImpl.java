package xyz.gestus.gestus.feature.logs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.feature.logs.dto.LogResponse;
import xyz.gestus.gestus.feature.logs.Log;
import xyz.gestus.gestus.feature.logs.LogRepository;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    private LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository){
        this.logRepository = logRepository;
    }

    @Override
    public List<LogResponse> getLogsByUserId(Long userId) {
        List<Log> logModels = logRepository.getLogModelByUserId(userId);

        List<LogResponse> response = new ArrayList<>();
        for(Log model :logModels){
            LogResponse responseDto = new LogResponse();
            responseDto.setDate(model.getDate());
            responseDto.setName(model.getName());

            response.add(responseDto);
        }

        return response;
    }
}
