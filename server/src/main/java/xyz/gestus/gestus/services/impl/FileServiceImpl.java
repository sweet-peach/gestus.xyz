package xyz.gestus.gestus.services.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.dto.DirRequestDto;
import xyz.gestus.gestus.dto.FileRequestDto;
import xyz.gestus.gestus.dto.FileResponseDto;
import xyz.gestus.gestus.exceptions.DirectoryAlreadyExistsException;
import xyz.gestus.gestus.exceptions.ProjectNotFoundException;
import xyz.gestus.gestus.models.FileModel;
import xyz.gestus.gestus.models.ProjectModel;
import xyz.gestus.gestus.repositories.FileRepository;
import xyz.gestus.gestus.repositories.ProjectRepository;
import xyz.gestus.gestus.services.FileService;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private static final String DIRECTORY_TYPE = "dir";
    private String storageDirectory;
    private FileRepository fileRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, ProjectRepository projectRepository) {
        this.fileRepository = fileRepository;
        this.projectRepository = projectRepository;
    }

    @PostConstruct
    public void init() {
        storageDirectory = createBaseDirectory();
    }

    private String createBaseDirectory() {
        Path baseDir = Paths.get("").toAbsolutePath().resolve("files");
        ensureDirectoryExists(baseDir);
        return baseDir.toString();
    }
    private void ensureDirectoryExists(Path path) {
        try {
            Files.createDirectories(path);
        } catch (Exception e) {
            throw new RuntimeException("Could not create directory at: " + path, e);
        }
    }

    @Override
    public void createProjectDir(String projectId) {
        ensureDirectoryExists(constructPath(storageDirectory, projectId));
    }

    @Override
    public FileResponseDto createDir(Long projectId, DirRequestDto dirRequestDto) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException("Project not found");
        }

        String filePath = constructFilePath(dirRequestDto);
        if (fileRepository.existsByPath(filePath)) {
            throw new DirectoryAlreadyExistsException("Directory already exists");
        }

        FileModel fileToCreate = buildFileModel(dirRequestDto, filePath);
        ensureDirectoryExists(Paths.get(storageDirectory, projectId.toString(), filePath));
        FileModel createdFile = fileRepository.save(fileToCreate);

        return mapEntityToResponse(createdFile);
    }

    private String constructFilePath(DirRequestDto dirRequestDto) {
        return fileRepository.findById(dirRequestDto.getParentId())
                .map(parentFile -> parentFile.getPath() + File.separator + dirRequestDto.getName())
                .orElse(dirRequestDto.getName());
    }

    private FileModel buildFileModel(DirRequestDto dirRequestDto, String filePath) {
        FileModel fileModel = new FileModel();
        fileModel.setName(dirRequestDto.getName());
        fileModel.setType(DIRECTORY_TYPE);
        fileModel.setSize(0L);
        fileModel.setDate(new Date());
        fileModel.setPath(filePath);
        return fileModel;
    }

    private FileResponseDto mapEntityToResponse(FileModel fileModel) {
        FileResponseDto response = new FileResponseDto();
        response.setId(fileModel.getId());
        response.setName(fileModel.getName());
        response.setType(fileModel.getType());
        response.setSize(fileModel.getSize());
        response.setPath(fileModel.getPath());
        response.setDate(fileModel.getDate());
        response.setParentId(Optional.ofNullable(fileModel.getParent()).map(FileModel::getId).orElse(null));
        return response;
    }

    private Path constructPath(String... parts) {
        return Paths.get("", parts);
    }
}
