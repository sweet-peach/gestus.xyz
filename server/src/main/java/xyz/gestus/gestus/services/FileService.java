package xyz.gestus.gestus.services;

import xyz.gestus.gestus.dto.DirRequestDto;
import xyz.gestus.gestus.dto.FileRequestDto;
import xyz.gestus.gestus.dto.FileResponseDto;

public interface FileService {
    public void createProjectDir(String projectId);
    public FileResponseDto createDir(Long projectId, DirRequestDto dirRequest);
}
