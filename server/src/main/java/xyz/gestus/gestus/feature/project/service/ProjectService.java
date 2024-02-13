package xyz.gestus.gestus.feature.project.service;

import xyz.gestus.gestus.feature.project.dto.ProjectRequest;
import xyz.gestus.gestus.feature.project.dto.ProjectResponse;
import xyz.gestus.gestus.feature.project.dto.ProjectSearchResponse;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectRequest projectRequest);
    ProjectResponse updateProject(Long projectId, ProjectRequest projectRequest);
    ProjectResponse getProjectById(Long id);
    List<ProjectResponse> getProjects();
    void deleteProject(Long projectId);
    List<ProjectSearchResponse> searchProjects(String query, List<String> keywords, String sortBy);

}
