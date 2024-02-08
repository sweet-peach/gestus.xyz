package xyz.gestus.gestus.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.gestus.gestus.dto.DirRequestDto;
import xyz.gestus.gestus.dto.FileResponseDto;
import xyz.gestus.gestus.services.FileService;

@RestController
@RequestMapping("/api/project/")
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<FileResponseDto> createDir(@PathVariable(value = "projectId") Long projectId, @Valid @RequestBody DirRequestDto dirRequest){
        return new ResponseEntity<>(fileService.createDir(projectId, dirRequest), HttpStatus.CREATED);
    }
}
