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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

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
        Path currentDirectoryPath = FileSystems.getDefault().getPath("");
        String baseDir = currentDirectoryPath.toAbsolutePath().toString();
        storageDirectory = Paths.get(baseDir, "files").toString();
        File projectDirectory = new File(storageDirectory);
        if (!projectDirectory.exists() && !projectDirectory.mkdirs()) {
            throw new RuntimeException("Could not create base directory for project files at: " + storageDirectory);
        }
    }

    @Override
    public void createProjectDir(String projectId) {
        Path projectPath = Paths.get(storageDirectory, projectId);
        File projectDirectory = new File(projectPath.toString());
        if (!projectDirectory.exists()) {
            projectDirectory.mkdirs();
        }
    }

    @Override
    public FileResponseDto createDir(Long projectId, DirRequestDto dirRequestDto) {
        Optional<FileModel> parentFileOptional = fileRepository.findById(dirRequestDto.getParentId());
        String filePath = "";

        if(!projectRepository.existsById(projectId)){
            throw new ProjectNotFoundException("Project not found");
        }

        FileModel fileToCreate = new FileModel();
        if (parentFileOptional.isPresent()) {
            FileModel parentFile = parentFileOptional.get();
            filePath = parentFile.getPath() + "\\" + dirRequestDto.getName();
            fileToCreate.setParent(parentFile);
        } else {
            filePath = dirRequestDto.getName();
        }

        if(fileRepository.existsByPath(filePath)){
            throw new DirectoryAlreadyExistsException("Directory already exists");
        }

        fileToCreate.setName(dirRequestDto.getName());
        fileToCreate.setType("dir");
        fileToCreate.setSize(0L);
        fileToCreate.setDate(new Date());
        fileToCreate.setPath(filePath);

        Path projectPath = Paths.get(storageDirectory,projectId.toString());
        Path absolutePath = Paths.get(projectPath.toString(), filePath);
        File directory = new File(absolutePath.toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        FileModel createdFile = fileRepository.save(fileToCreate);

        if(parentFileOptional.isPresent()){
            FileModel parentFile = parentFileOptional.get();
            parentFile.addChild(createdFile);
            fileRepository.save(parentFile);
        }

        return mapEntityToResponse(createdFile);
    }

    private FileResponseDto mapEntityToResponse(FileModel fileModel){
        FileResponseDto response = new FileResponseDto();
        response.setId(fileModel.getId());
        response.setName(fileModel.getName());
        response.setType(fileModel.getType());
        response.setSize(fileModel.getSize());
        response.setPath(fileModel.getPath());
        response.setDate(fileModel.getDate());;
        if (fileModel.getParent() != null) {
            response.setParentId(fileModel.getParent().getId());
        } else {
            response.setParentId(null);
        }

        return response;
    }
}
