package xyz.gestus.gestus.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.gestus.gestus.annotations.Log;
import xyz.gestus.gestus.feature.project.file.dto.DirRequestDto;
import xyz.gestus.gestus.feature.project.file.dto.FileResponseDto;
import xyz.gestus.gestus.feature.project.file.service.FileService;

import java.util.List;

@RestController
@RequestMapping("/api/project/{projectId}/files")
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    @Log(name = "A user has created a directory")
    public ResponseEntity<FileResponseDto> createDir(@PathVariable Long projectId, @Valid @RequestBody DirRequestDto dirRequest){
        return new ResponseEntity<>(fileService.createDir(projectId, dirRequest), HttpStatus.OK);
    }



    @PostMapping("/upload")
    @Log(name = "A user has uploaded a file")
    public ResponseEntity<FileResponseDto> uploadFile(@PathVariable Long projectId,
                                                      @RequestParam("file")MultipartFile file,
                                                      @RequestParam("parentId") Long parentId){

        return ResponseEntity.ok(fileService.uploadFile(projectId,parentId,file));
    }

    @DeleteMapping("{fileId}")
    @Log(name = "A user has deleted a file")
    public ResponseEntity<String> deleteFile(@PathVariable Long projectId, @PathVariable Long fileId){
        fileService.deleteFileRecursively(projectId, fileId);
        return ResponseEntity.ok("File deleted");
    }
    @GetMapping
    public ResponseEntity<List<FileResponseDto>> getFilesOfProjectById(@PathVariable Long projectId, @RequestParam(name = "parent", required = false) Long parentId){
        return new ResponseEntity<>(fileService.getFilesOfProjectByParentId(projectId,parentId),HttpStatus.OK);
    }

    @GetMapping("{fileId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long projectId, @PathVariable Long fileId) {
        Resource resource = fileService.downloadFile(projectId, fileId);
        String contentType = "application/octet-stream";
        String fileName = resource.getFilename();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @GetMapping("{fileId}")
    public ResponseEntity<FileResponseDto> getFile(@PathVariable Long projectId, @PathVariable Long fileId){
        return new ResponseEntity<>(fileService.getFileByIdAndProjectId(projectId, fileId),HttpStatus.OK);
    }
}
