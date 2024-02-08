package xyz.gestus.gestus.services;

import xyz.gestus.gestus.dto.ProjectRequestDto;
import xyz.gestus.gestus.dto.ProjectResponseDto;

import java.util.List;

public interface ProjectService {
    public ProjectResponseDto createProject(ProjectRequestDto projectRequest);
    public ProjectResponseDto updateProject(Long projectId,ProjectRequestDto projectRequest);

    public List<ProjectResponseDto> getProjects();

}
