package xyz.gestus.gestus.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.dto.KeywordRequestDto;
import xyz.gestus.gestus.dto.KeywordResponseDto;
import xyz.gestus.gestus.dto.ProjectRequestDto;
import xyz.gestus.gestus.dto.ProjectResponseDto;
import xyz.gestus.gestus.exceptions.ProjectNotFoundException;
import xyz.gestus.gestus.models.KeywordModel;
import xyz.gestus.gestus.models.ProjectModel;
import xyz.gestus.gestus.repositories.KeywordRepository;
import xyz.gestus.gestus.repositories.ProjectRepository;
import xyz.gestus.gestus.services.ProjectService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    KeywordRepository keywordRepository;
    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,KeywordRepository keywordRepository) {
        this.projectRepository = projectRepository;
        this.keywordRepository = keywordRepository;
    }

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequest) {
        ProjectModel project = mapToEntityByRequest(new ProjectModel(),projectRequest);
        project.setCreationDate(new Date());
        project.setUpdateDate(new Date());

        ProjectModel createdProjectEntity = projectRepository.save(project);

        return mapEntityToResponse(createdProjectEntity);
    }

    @Override
    public ProjectResponseDto updateProject(Long projectId, ProjectRequestDto projectRequest) {
        ProjectModel project = projectRepository.findById(projectId).orElseThrow(()-> new ProjectNotFoundException("Project not found"));
        ProjectModel updatedProject = mapToEntityByRequest(project,projectRequest);
        ProjectModel savedProject = projectRepository.save(updatedProject);
        return mapEntityToResponse(savedProject);
    }

    @Override
    public List<ProjectResponseDto> getProjects() {
        List<ProjectModel> projects = projectRepository.findAll();
        List<ProjectResponseDto> formattedProjects = new ArrayList<>();
        for(ProjectModel project : projects){
            formattedProjects.add(mapEntityToResponse(project));
        }
        return formattedProjects;
    }

    private ProjectModel mapToEntityByRequest(ProjectModel project,ProjectRequestDto projectRequest){
        project.setExecutionStart(projectRequest.getExecutionStart());
        project.setExecutionEnd(projectRequest.getExecutionEnd());
        project.setRating(projectRequest.getRating());
        project.setName(projectRequest.getName());
        project.setType(projectRequest.getType());
        project.setPhase(projectRequest.getPhase());
        project.setAuditor(projectRequest.getAuditor());
        project.setCode(projectRequest.getCode());
        project.setIsActive(projectRequest.getIsActive());
        project.setInCooperation(projectRequest.getInCooperation());

        if (projectRequest.getKeywords() != null && !projectRequest.getKeywords().isEmpty()) {
            List<KeywordModel> keywords = keywordRepository.findAllById(projectRequest.getKeywords());
            project.setKeywords(keywords);
        }

        return project;
    }


    private ProjectResponseDto mapEntityToResponse(ProjectModel projectModel){
        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
        projectResponseDto.setId(projectModel.getId());
        projectResponseDto.setName(projectModel.getName());
        projectResponseDto.setCreationDate(projectModel.getCreationDate());
        projectResponseDto.setUpdateDate(projectModel.getUpdateDate());
        projectResponseDto.setExecutionStart(projectModel.getExecutionStart());
        projectResponseDto.setExecutionEnd(projectModel.getExecutionEnd());
        projectResponseDto.setRating(projectModel.getRating());
        projectResponseDto.setType(projectModel.getType());
        projectResponseDto.setPhase(projectModel.getPhase());
        projectResponseDto.setIsActive(projectModel.getIsActive());
        projectResponseDto.setAuditor(projectModel.getAuditor());
        projectResponseDto.setCode(projectModel.getCode());
        projectResponseDto.setInCooperation(projectModel.getInCooperation());

        List<KeywordResponseDto> keywords = projectModel.getKeywords().stream()
                .map(keyword -> new KeywordResponseDto(keyword.getId(), keyword.getName()))
                .collect(Collectors.toList());
        
        projectResponseDto.setKeywords(keywords);

        return projectResponseDto;
    }
}
