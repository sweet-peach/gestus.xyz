package xyz.gestus.gestus.feature.project.file.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import xyz.gestus.gestus.feature.project.file.dto.DirRequestDto;
import xyz.gestus.gestus.feature.project.file.dto.FileResponseDto;

import java.util.List;

public interface FileService {
    public void createProjectDir(String projectId);

    public void deleteProjectDir(Long projectId);
    public FileResponseDto createDir(Long projectId, DirRequestDto dirRequest);
    public FileResponseDto uploadFile(Long projectId, Long parentId, MultipartFile file);

    public void deleteFileRecursively(Long projectId, Long fileId);
    Resource downloadFile(Long projectId, Long fileId);

    public List<FileResponseDto> getFilesByProject(Long projectId);
    public List<FileResponseDto> getFilesOfProjectByParentId(Long projectId, Long parentId);
    public FileResponseDto getFileByIdAndProjectId(Long fileId, Long projectId);
}
