package xyz.gestus.gestus.feature.file.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.gestus.gestus.feature.file.dto.FileResponse;
import xyz.gestus.gestus.feature.file.exception.DirectoryAlreadyExistsException;
import xyz.gestus.gestus.feature.file.exception.FileNotFoundException;
import xyz.gestus.gestus.feature.file.dto.DirRequest;
import xyz.gestus.gestus.feature.file.exception.NoMoreSpaceException;
import xyz.gestus.gestus.feature.project.exception.ProjectNotFoundException;
import xyz.gestus.gestus.feature.file.File;
import xyz.gestus.gestus.feature.project.Project;
import xyz.gestus.gestus.feature.file.exception.UploadFailException;
import xyz.gestus.gestus.feature.file.FileRepository;
import xyz.gestus.gestus.feature.project.ProjectRepository;

import java.io.IOException;
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

    @Value("${STORAGE_LIMIT}")
    private Long storageLimit;


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
        Path projectPath = Paths.get(storageDirectory + java.io.File.separator + projectId);
        try {
            Files.deleteIfExists(projectPath);
        } catch (IOException e) {
        }

    }


    @Override
    public FileResponse createDir(Long projectId, DirRequest dirRequestDto) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        File parentFile = fileRepository.findByIdAndProjectId(dirRequestDto.getParentId(), projectId);

        String fileName = dirRequestDto.getName();
        String filePath = constructFilePath(parentFile, fileName);

        if (fileRepository.existsByPath(filePath)) {
            throw new DirectoryAlreadyExistsException("Directory already exists");
        }

        File fileToCreate = buildFileModel(fileName, parentFile, filePath);
        fileToCreate.setProject(project);

        ensureDirectoryExists(Paths.get(storageDirectory, projectId.toString(), filePath));
        File createdFile = fileRepository.save(fileToCreate);

        if (parentFile != null) {
            parentFile.addChild(createdFile);
        }

        return mapEntityToResponse(createdFile);
    }

    @Override
    public FileResponse uploadFile(Long projectId, Long parentId, MultipartFile file) {
        Project projectModel = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        File parentFile = fileRepository.findByIdAndProjectId(parentId, projectId);

        String fileName = file.getOriginalFilename();
        String filePath = constructFilePath(parentFile, fileName);

        if (fileRepository.existsByPath(filePath)) {
            throw new DirectoryAlreadyExistsException("Directory already exists");
        }

        java.io.File storageFolder = new java.io.File(storageDirectory);
        Long storageFolderSize = getFolderSize(storageFolder);

        if(file.getSize() > storageLimit - storageFolderSize){
            throw new NoMoreSpaceException("Not enough space to upload the file");
        }

        Path absolutePath = Paths.get(storageDirectory + java.io.File.separator + projectId.toString() + java.io.File.separator + filePath);

        try {
            file.transferTo(absolutePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UploadFailException("An error occurred while downloading the file");
        }

        File fileToCreate = new File();
        fileToCreate.setName(file.getOriginalFilename());
        fileToCreate.setType(file.getContentType());
        fileToCreate.setSize(file.getSize());
        fileToCreate.setPath(filePath);
        fileToCreate.setDate(new Date());
        fileToCreate.setParent(parentFile);
        fileToCreate.setProject(projectModel);

        File createdFile = fileRepository.save(fileToCreate);

        if (parentFile != null) {
            parentFile.addChild(createdFile);
        }

        return mapEntityToResponse(createdFile);
    }

    @Override
    public void deleteFileRecursively(Long projectId, Long fileId) {
        Project projectModel = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found"));


        File parent = file.getParent();
        if (parent != null) {
            parent.removeChild(parent);
        }

        deleteFileAndChildren(file, projectId);
    }

    @Override
    public Resource downloadFile(Long projectId, Long fileId) {
        System.out.println(fileId);
        System.out.println(projectId);
        File file = fileRepository.findByIdAndProjectId(fileId, projectId);
        if (file == null) {
            throw new FileNotFoundException("File not found");
        }

        Path filePath = Paths.get(storageDirectory, projectId.toString(), file.getPath()).normalize();
        System.out.println(filePath);
        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists() || !resource.isReadable()) {
            throw new FileNotFoundException("File not found " + fileId);
        }

        return resource;
    }

    @Override
    public List<FileResponse> getFilesByProject(Long projectId) {
        Project projectModel = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        List<File> files = fileRepository.findByProjectId(projectId);

        List<FileResponse> formattedFiles = new ArrayList<>();
        for (File file : files) {
            formattedFiles.add(mapEntityToResponse(file));
        }

        return formattedFiles;
    }

    @Override
    public List<FileResponse> getFilesOfProjectByParentId(Long projectId, Long parentId) {
        Project projectModel = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        List<File> files = fileRepository.findAllByParentIdAndProjectId(parentId, projectId);
        List<FileResponse> formattedFiles = new ArrayList<>();
        for (File file : files) {
            formattedFiles.add(mapEntityToResponse(file));
        }
        return formattedFiles;
    }

    @Override
    public FileResponse getFileByIdAndProjectId(Long fileId, Long projectId) {
        File file = fileRepository.findByIdAndProjectId(fileId, projectId);
        if (file == null) {
            throw new FileNotFoundException("File not found");
        }

        return mapEntityToResponse(file);
    }

    private void deleteFileAndChildren(File file, Long projectId) {
        List<File> childFiles = file.getChilds();
        for (File child : childFiles) {
            deleteFileAndChildren(child, projectId);
        }

        if (file.getParent() != null) {
            file.getParent().removeChild(file);
        }

        fileRepository.delete(file);

        Path absolutePath = Paths.get(storageDirectory, projectId.toString(), file.getPath());

        try {
            Files.deleteIfExists(absolutePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file on disk: " + absolutePath, e);
        }
    }


    private String constructFilePath(File parentFile, String fileName) {
        if (parentFile == null) {
            return fileName;
        } else {
            return parentFile.getPath() + java.io.File.separator + fileName;
        }
    }


    private String constructFilePath(DirRequest dirRequestDto) {
        if (dirRequestDto.getParentId() == null) {
            return dirRequestDto.getName();
        }
        return fileRepository.findById(dirRequestDto.getParentId())
                .map(parentFile -> parentFile.getPath() + java.io.File.separator + dirRequestDto.getName())
                .orElse(dirRequestDto.getName());
    }

    private String constructFilePath(Long parentId, String fileName) {
        return fileRepository.findById(parentId)
                .map(parentFile -> parentFile.getPath() + java.io.File.separator + fileName)
                .orElse(fileName);
    }

    private File buildFileModel(String fileName, File parentFile, String filePath) {
        File file = new File();
        file.setName(fileName);
        file.setType(DIRECTORY_TYPE);
        file.setSize(0L);
        file.setDate(new Date());
        file.setPath(filePath);
        file.setParent(parentFile);
        return file;
    }

    private FileResponse mapEntityToResponse(File file) {
        FileResponse response = new FileResponse();
        response.setId(file.getId());
        response.setName(file.getName());
        response.setType(file.getType());
        response.setSize(file.getSize());
        response.setPath(file.getPath());
        response.setDate(file.getDate());
        response.setParentId(Optional.ofNullable(file.getParent()).map(File::getId).orElse(null));
        return response;
    }

    public static long getFolderSize(java.io.File folder) {
        long length = 0;
        java.io.File[] files = folder.listFiles();

        if (files != null) {
            for (java.io.File file : files) {
                if (file.isFile()) {
                    length += file.length();
                } else {
                    length += getFolderSize(file);
                }
            }
        }

        return length;
    }

    private Path constructPath(String... parts) {
        return Paths.get("", parts);
    }
}
