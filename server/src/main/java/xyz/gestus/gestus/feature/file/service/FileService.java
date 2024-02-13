package xyz.gestus.gestus.feature.file.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import xyz.gestus.gestus.feature.file.dto.FileResponse;
import xyz.gestus.gestus.feature.file.dto.DirRequest;

import java.util.List;

public interface FileService {
    public void createProjectDir(String projectId);

    public void deleteProjectDir(Long projectId);
    public FileResponse createDir(Long projectId, DirRequest dirRequest);
    public FileResponse uploadFile(Long projectId, Long parentId, MultipartFile file);

    public void deleteFileRecursively(Long projectId, Long fileId);
    Resource downloadFile(Long projectId, Long fileId);

    public List<FileResponse> getFilesByProject(Long projectId);
    public List<FileResponse> getFilesOfProjectByParentId(Long projectId, Long parentId);
    public FileResponse getFileByIdAndProjectId(Long fileId, Long projectId);
}
