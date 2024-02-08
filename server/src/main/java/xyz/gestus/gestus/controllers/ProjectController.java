package xyz.gestus.gestus.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.gestus.gestus.dto.ProjectRequestDto;
import xyz.gestus.gestus.dto.ProjectResponseDto;
import xyz.gestus.gestus.dto.RegistrationRequestDto;
import xyz.gestus.gestus.models.ProjectModel;
import xyz.gestus.gestus.repositories.ProjectRepository;
import xyz.gestus.gestus.services.FileService;
import xyz.gestus.gestus.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private FileService fileService;
    private ProjectService projectService;

    public ProjectController(FileService fileService, ProjectService projectService, ProjectRepository projectRepository) {
        this.fileService = fileService;
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getProjects(
            @RequestParam(name = "keywords") List<Long> keywordsId,
            @RequestParam(required = false) String name) {
        return new ResponseEntity<>(projectService.getProjectsByKeywordsAndName(keywordsId, name), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(@Valid @RequestBody ProjectRequestDto projectRequestDto) {
        ProjectResponseDto createdProject = projectService.createProject(projectRequestDto);
        fileService.createProjectDir(createdProject.getId().toString());
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable(value = "projectId") Long projectId, @Valid @RequestBody ProjectRequestDto projectRequestDto) {
        ProjectResponseDto createdProject = projectService.updateProject(projectId, projectRequestDto);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable(value = "projectId") Long projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>("Project deleted", HttpStatus.CREATED);
    }
}
