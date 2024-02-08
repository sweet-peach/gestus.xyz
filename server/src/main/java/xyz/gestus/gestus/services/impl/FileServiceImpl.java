package xyz.gestus.gestus.services.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.services.FileService;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    private String storageDirectory;

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
    public void createProjectDir(String dirName) {
        Path projectPath = Paths.get(storageDirectory, dirName);
        File projectDirectory = new File(projectPath.toString());
        if(!projectDirectory.exists()){
            projectDirectory.mkdirs();
        }
    }
}
