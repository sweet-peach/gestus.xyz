package xyz.gestus.gestus.feature.project;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.gestus.gestus.core.annotations.Log;
import xyz.gestus.gestus.feature.project.dto.ProjectRequest;
import xyz.gestus.gestus.feature.project.dto.ProjectResponse;
import xyz.gestus.gestus.feature.file.service.FileService;
import xyz.gestus.gestus.feature.project.dto.ProjectSearchResponse;
import xyz.gestus.gestus.feature.project.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private FileService fileService;
    private ProjectService projectService;

    @Autowired
    public ProjectController(FileService fileService, ProjectService projectService) {
        this.fileService = fileService;
        this.projectService = projectService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long projectId){
        return new ResponseEntity<>(projectService.gerProjectById(projectId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProjectSearchResponse>> getProject(@RequestParam(name = "name", required = false) String name,
                                                                  @RequestParam(name = "keywords", required = false) List<String> keywords,
                                                                  @RequestParam(name = "sortBy", required = false) String sortBy){
        return new ResponseEntity<>(projectService.searchProjects(name,keywords,sortBy),HttpStatus.OK);
    }

    @PostMapping
    @Log(name = "A user has created a project")
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest projectRequestDto) {
        ProjectResponse createdProject = projectService.createProject(projectRequestDto);
        fileService.createProjectDir(createdProject.getId().toString());
        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    @PutMapping("/{projectId}")
    @Log(name = "A user has updated the project")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable(value = "projectId") Long projectId, @Valid @RequestBody ProjectRequest projectRequestDto) {
        ProjectResponse createdProject = projectService.updateProject(projectId, projectRequestDto);
        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    @Log(name = "A user has deleted the project")
    public ResponseEntity<String> deleteProject(@PathVariable(value = "projectId") Long projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>("Project deleted", HttpStatus.OK);
    }
}
