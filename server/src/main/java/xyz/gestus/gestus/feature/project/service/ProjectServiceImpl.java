package xyz.gestus.gestus.feature.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.gestus.gestus.feature.keyword.dto.KeywordResponseDto;
import xyz.gestus.gestus.feature.project.dto.ProjectRequestDto;
import xyz.gestus.gestus.feature.project.dto.ProjectResponseDto;
import xyz.gestus.gestus.feature.project.exception.ProjectNotFoundException;
import xyz.gestus.gestus.feature.project.file.File;
import xyz.gestus.gestus.feature.keyword.Keyword;
import xyz.gestus.gestus.feature.project.Project;
import xyz.gestus.gestus.feature.project.file.FileRepository;
import xyz.gestus.gestus.feature.keyword.KeywordRepository;
import xyz.gestus.gestus.feature.project.ProjectRepository;
import xyz.gestus.gestus.feature.project.file.service.FileService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    KeywordRepository keywordRepository;
    FileRepository fileRepository;
    FileService fileService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, KeywordRepository keywordRepository, FileRepository fileRepository, FileService fileService) {
        this.projectRepository = projectRepository;
        this.keywordRepository = keywordRepository;
        this.fileRepository = fileRepository;
        this.fileService = fileService;
    }

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequest) {
        Project project = mapToEntityByRequest(new Project(), projectRequest);
        project.setCreationDate(new Date());
        project.setUpdateDate(new Date());

        Project createdProjectEntity = projectRepository.save(project);

        return mapEntityToResponse(createdProjectEntity);
    }

    @Override
    public ProjectResponseDto updateProject(Long projectId, ProjectRequestDto projectRequest) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        Project updatedProject = mapToEntityByRequest(project, projectRequest);
        Project savedProject = projectRepository.save(updatedProject);
        return mapEntityToResponse(savedProject);
    }

    @Override
    public List<ProjectResponseDto> getProjects() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectResponseDto> formattedProjects = new ArrayList<>();
        for (Project project : projects) {
            formattedProjects.add(mapEntityToResponse(project));
        }
        return formattedProjects;
    }

    @Override
    public List<ProjectResponseDto> getProjectsByKeywordsAndName(List<Long> keywordsId, String name) {
        if (CollectionUtils.isEmpty(keywordsId)) {
            return Collections.emptyList();
        }

        List<Project> projects = projectRepository.findByKeywordsIdAndNameContaining(keywordsId, name);
        List<ProjectResponseDto> formattedProjects = new ArrayList<>();
        for (Project project : projects) {
            formattedProjects.add(mapEntityToResponse(project));
        }

        return formattedProjects;
    }

    @Override
    public List<ProjectResponseDto> getProjectsByName(String name) {
        List<Project> projects = projectRepository.findByNameContaining(name);
        List<ProjectResponseDto> formattedProjects = new ArrayList<>();
        for (Project project : projects) {
            formattedProjects.add(mapEntityToResponse(project));
        }

        return formattedProjects;
    }

    @Override
    public ProjectResponseDto gerProjectById(Long id) {
        Project model = projectRepository.findById(id).orElseThrow(()-> new ProjectNotFoundException("Project not found"));

        return mapEntityToResponse(model);
    }

    @Override
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        List<File> files = fileRepository.findByProjectId(project.getId());
        for(File file : files){
            fileService.deleteFileRecursively(projectId,file.getId());
        }

        project.getKeywords().forEach(keyword -> keyword.getProjects().remove(project));
        fileService.deleteProjectDir(projectId);
        projectRepository.delete(project);
    }


    private Project mapToEntityByRequest(Project project, ProjectRequestDto projectRequest) {
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
            List<Keyword> keywords = keywordRepository.findAllById(projectRequest.getKeywords());
            project.setKeywords(keywords);
        }

        return project;
    }


    private ProjectResponseDto mapEntityToResponse(Project projectModel) {
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
