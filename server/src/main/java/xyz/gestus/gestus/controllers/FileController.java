package xyz.gestus.gestus.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.gestus.gestus.dto.DirRequestDto;
import xyz.gestus.gestus.dto.FileRequestDto;
import xyz.gestus.gestus.dto.FileResponseDto;
import xyz.gestus.gestus.services.FileService;

@RestController
@RequestMapping("/api/project/{projectId}/files")
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<FileResponseDto> createDir(@PathVariable Long projectId, @Valid @RequestBody DirRequestDto dirRequest){
        return new ResponseEntity<>(fileService.createDir(projectId, dirRequest), HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    public ResponseEntity<FileResponseDto> uploadFile(@PathVariable Long projectId,
                                                      @RequestParam("file")MultipartFile file,
                                                      @RequestParam("parentId") Long parentId){


        return ResponseEntity.ok(fileService.uploadFile(projectId,parentId,file));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFile(@PathVariable Long projectId, @RequestParam(name = "id", required = true) Long fileId){
        fileService.deleteFileRecursively(projectId, fileId);
        return ResponseEntity.ok("File deleted");
    }
}
