package xyz.gestus.gestus.feature.project.service;

import xyz.gestus.gestus.feature.project.dto.ProjectRequestDto;
import xyz.gestus.gestus.feature.project.dto.ProjectResponseDto;

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
