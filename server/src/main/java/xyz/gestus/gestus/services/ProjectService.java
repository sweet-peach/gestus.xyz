package xyz.gestus.gestus.services;

import xyz.gestus.gestus.dto.ProjectRequestDto;
import xyz.gestus.gestus.dto.ProjectResponseDto;
import xyz.gestus.gestus.models.ProjectModel;

import java.util.List;

public interface ProjectService {
    public ProjectResponseDto createProject(ProjectRequestDto projectRequest);
    public ProjectResponseDto updateProject(Long projectId,ProjectRequestDto projectRequest);

    public List<ProjectResponseDto> getProjects();
    public List<ProjectResponseDto> getProjectsByKeywordsAndName(List<Long> keywordsId, String name);
    public List<ProjectResponseDto> getProjectsByName(String name);
    public ProjectResponseDto gerProjectById(Long id);
    public void deleteProject(Long projectId);

}
