package xyz.gestus.gestus.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.dto.ProjectRequestDto;
import xyz.gestus.gestus.dto.ProjectResponseDto;
import xyz.gestus.gestus.exceptions.ProjectNotFoundException;
import xyz.gestus.gestus.models.ProjectModel;
import xyz.gestus.gestus.repositories.ProjectRepository;
import xyz.gestus.gestus.services.ProjectService;

import java.util.Date;

@Service
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequest) {
        ProjectModel project = mapEntityByRequest(new ProjectModel(),projectRequest);
        project.setCreationDate(new Date());
        project.setUpdateDate(new Date());

        ProjectModel createdProjectEntity = projectRepository.save(project);

        return mapEntityToResponse(createdProjectEntity);
    }

    @Override
    public ProjectResponseDto updateProject(Long projectId, ProjectRequestDto projectRequest) {
        ProjectModel project = projectRepository.findById(projectId).orElseThrow(()-> new ProjectNotFoundException("Project not found"));
        ProjectModel updatedProject = mapEntityByRequest(project,projectRequest);
        ProjectModel savedProject = projectRepository.save(updatedProject);
        return mapEntityToResponse(savedProject);
    }

    private ProjectModel mapEntityByRequest(ProjectModel project,ProjectRequestDto projectRequest){
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

        return projectResponseDto;
    }
}
