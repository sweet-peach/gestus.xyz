package xyz.gestus.gestus.services;

import org.springframework.web.multipart.MultipartFile;
import xyz.gestus.gestus.dto.DirRequestDto;
import xyz.gestus.gestus.dto.FileRequestDto;
import xyz.gestus.gestus.dto.FileResponseDto;
import xyz.gestus.gestus.dto.FileUploadDto;

public interface FileService {
    public void createProjectDir(String projectId);
    public FileResponseDto createDir(Long projectId, DirRequestDto dirRequest);
    public FileResponseDto uploadFile(Long projectId, Long parentId, MultipartFile file);

}
