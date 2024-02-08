package xyz.gestus.gestus.services.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.gestus.gestus.dto.DirRequestDto;
import xyz.gestus.gestus.dto.FileRequestDto;
import xyz.gestus.gestus.dto.FileResponseDto;
import xyz.gestus.gestus.exceptions.DirectoryAlreadyExistsException;
import xyz.gestus.gestus.exceptions.FileNotFoundException;
import xyz.gestus.gestus.exceptions.ProjectNotFoundException;
import xyz.gestus.gestus.exceptions.UploadFailException;
import xyz.gestus.gestus.models.FileModel;
import xyz.gestus.gestus.models.ProjectModel;
import xyz.gestus.gestus.repositories.FileRepository;
import xyz.gestus.gestus.repositories.ProjectRepository;
import xyz.gestus.gestus.services.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    public void deleteProjectDir(Long projectId) {
        Path projectPath = Paths.get(storageDirectory + File.separator + projectId);
        try {
            Files.deleteIfExists(projectPath);
        } catch (IOException e) {}

    }



    @Override
    public FileResponseDto createDir(Long projectId, DirRequestDto dirRequestDto) {
        ProjectModel project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        FileModel parentFile = fileRepository.findByIdAndProjectId(dirRequestDto.getParentId(), projectId);

        String fileName = dirRequestDto.getName();
        String filePath = constructFilePath(parentFile,fileName);

        if (fileRepository.existsByPath(filePath)) {
            throw new DirectoryAlreadyExistsException("Directory already exists");
        }

        FileModel fileToCreate = buildFileModel(fileName, parentFile, filePath);
        fileToCreate.setProject(project);

        ensureDirectoryExists(Paths.get(storageDirectory, projectId.toString(), filePath));
        FileModel createdFile = fileRepository.save(fileToCreate);

        if(parentFile != null){
            parentFile.addChild(createdFile);
        }

        return mapEntityToResponse(createdFile);
    }

    @Override
    public FileResponseDto uploadFile(Long projectId, Long parentId, MultipartFile file) {
        ProjectModel projectModel = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        FileModel parentFile = fileRepository.findByIdAndProjectId(parentId,projectId);

        String fileName = file.getOriginalFilename();
        String filePath = constructFilePath(parentFile,fileName);

        if (fileRepository.existsByPath(filePath)) {
            throw new DirectoryAlreadyExistsException("Directory already exists");
        }

        Path absolutePath = Paths.get(storageDirectory + File.separator + projectId.toString() + File.separator + filePath);

        try {
            file.transferTo(absolutePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UploadFailException("An error occurred while downloading the file");
        }

        FileModel fileToCreate = new FileModel();
        fileToCreate.setName(file.getOriginalFilename());
        fileToCreate.setType(file.getContentType());
        fileToCreate.setSize(file.getSize());
        fileToCreate.setPath(filePath);
        fileToCreate.setDate(new Date());
        fileToCreate.setParent(parentFile);
        fileToCreate.setProject(projectModel);

        FileModel createdFile = fileRepository.save(fileToCreate);

        if(parentFile != null){
            parentFile.addChild(createdFile);
        }

        return mapEntityToResponse(createdFile);
    }

    @Override
    public void deleteFileRecursively(Long projectId, Long fileId) {
        ProjectModel projectModel = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        FileModel file = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found"));

        FileModel parent = file.getParent();
        if(parent != null){
            parent.removeChild(parent);
        }

        deleteFileAndChildren(file, projectId);
    }

    @Override
    public List<FileResponseDto> getFilesByProject(Long projectId) {
        ProjectModel projectModel = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        List<FileModel> files = fileRepository.findByProjectId(projectId);

        List<FileResponseDto> formattedFiles = new ArrayList<>();
        for(FileModel file: files){
            formattedFiles.add(mapEntityToResponse(file));
        }

        return formattedFiles;
    }

    @Override
    public List<FileResponseDto> getFilesOfProjectByParentId(Long projectId, Long parentId) {
        ProjectModel projectModel = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        List<FileModel> files = fileRepository.findAllByParentIdAndProjectId(parentId,projectId);
        List<FileResponseDto> formattedFiles = new ArrayList<>();
        for(FileModel file: files){
            formattedFiles.add(mapEntityToResponse(file));
        }
        return formattedFiles;
    }

    @Override
    public FileResponseDto getFileByIdAndProjectId(Long fileId, Long projectId) {
        FileModel fileModel = fileRepository.findByIdAndProjectId(fileId,projectId);
        if(fileModel == null){
            throw new FileNotFoundException("File not found");
        }

        return mapEntityToResponse(fileModel);
    }

    private void deleteFileAndChildren(FileModel file, Long projectId) {
        List<FileModel> childFiles = file.getChilds();
        for (FileModel child : childFiles) {
            deleteFileAndChildren(child, projectId);
        }

        fileRepository.delete(file);

        Path absolutePath = Paths.get(storageDirectory, projectId.toString(), file.getPath());

        try {
            Files.deleteIfExists(absolutePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file on disk: " + absolutePath, e);
        }
    }


    private String constructFilePath(FileModel parentFile, String fileName) {
        if (parentFile == null) {
            return fileName;
        } else {
            return parentFile.getPath() + File.separator + fileName;
        }
    }


    private String constructFilePath(DirRequestDto dirRequestDto) {
        if (dirRequestDto.getParentId() == null) {
            return dirRequestDto.getName();
        }
        return fileRepository.findById(dirRequestDto.getParentId())
                .map(parentFile -> parentFile.getPath() + File.separator + dirRequestDto.getName())
                .orElse(dirRequestDto.getName());
    }

    private String constructFilePath(Long parentId, String fileName) {
        return fileRepository.findById(parentId)
                .map(parentFile -> parentFile.getPath() + File.separator + fileName)
                .orElse(fileName);
    }

    private FileModel buildFileModel(String fileName, FileModel parentFile, String filePath) {
        FileModel fileModel = new FileModel();
        fileModel.setName(fileName);
        fileModel.setType(DIRECTORY_TYPE);
        fileModel.setSize(0L);
        fileModel.setDate(new Date());
        fileModel.setPath(filePath);
        fileModel.setParent(parentFile);
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
