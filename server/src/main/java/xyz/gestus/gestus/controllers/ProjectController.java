package xyz.gestus.gestus.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.gestus.gestus.annotations.Log;
import xyz.gestus.gestus.dto.ProjectRequestDto;
import xyz.gestus.gestus.dto.ProjectResponseDto;
import xyz.gestus.gestus.dto.RegistrationRequestDto;
import xyz.gestus.gestus.models.ProjectModel;
import xyz.gestus.gestus.repositories.ProjectRepository;
import xyz.gestus.gestus.services.FileService;
import xyz.gestus.gestus.services.ProjectService;

import java.util.ArrayList;
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

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> getProject(@PathVariable Long projectId){
        return new ResponseEntity<>(projectService.gerProjectById(projectId),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getProjects(
            @RequestParam(required = false) List<Long> keywords,
            @RequestParam(required = false) String query) {

        System.out.println(keywords);
        //TODO TO CHANGE
        System.out.println(query);

        if(keywords == null) keywords = new ArrayList<>();
        if(query == null) query = "";

        if(keywords.isEmpty()){
            return new ResponseEntity<>(projectService.getProjectsByName(query), HttpStatus.OK);
        }
        return new ResponseEntity<>(projectService.getProjectsByKeywordsAndName(keywords, query), HttpStatus.OK);
    }

    @PostMapping
    @Log(name = "A user has created a project")
    public ResponseEntity<ProjectResponseDto> createProject(@Valid @RequestBody ProjectRequestDto projectRequestDto) {
        ProjectResponseDto createdProject = projectService.createProject(projectRequestDto);
        fileService.createProjectDir(createdProject.getId().toString());
        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    @PutMapping("/{projectId}")
    @Log(name = "A user has updated the project")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable(value = "projectId") Long projectId, @Valid @RequestBody ProjectRequestDto projectRequestDto) {
        ProjectResponseDto createdProject = projectService.updateProject(projectId, projectRequestDto);
        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    @Log(name = "A user has deleted the project")
    public ResponseEntity<String> deleteProject(@PathVariable(value = "projectId") Long projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>("Project deleted", HttpStatus.OK);
    }
}
