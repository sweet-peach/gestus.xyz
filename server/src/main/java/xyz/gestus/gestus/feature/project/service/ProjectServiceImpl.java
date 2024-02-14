package xyz.gestus.gestus.feature.project.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.feature.keyword.dto.KeywordResponse;
import xyz.gestus.gestus.feature.project.dto.ProjectRequest;
import xyz.gestus.gestus.feature.project.dto.ProjectResponse;
import xyz.gestus.gestus.feature.project.dto.ProjectSearchResponse;
import xyz.gestus.gestus.feature.project.elastic.ProjectDocument;
import xyz.gestus.gestus.feature.project.elastic.ProjectSearchRepository;
import xyz.gestus.gestus.feature.project.exception.ProjectNotFoundException;
import xyz.gestus.gestus.feature.file.File;
import xyz.gestus.gestus.feature.keyword.Keyword;
import xyz.gestus.gestus.feature.project.Project;
import xyz.gestus.gestus.feature.file.FileRepository;
import xyz.gestus.gestus.feature.keyword.KeywordRepository;
import xyz.gestus.gestus.feature.project.ProjectRepository;
import xyz.gestus.gestus.feature.file.service.FileService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    KeywordRepository keywordRepository;
    FileRepository fileRepository;
    FileService fileService;
    ProjectSearchRepository projectSearchRepository;
    ElasticsearchClient elasticsearchClient;


    @Autowired
    public ProjectServiceImpl(ElasticsearchClient elasticsearchClient, ProjectSearchRepository projectSearchRepository, ProjectRepository projectRepository, KeywordRepository keywordRepository, FileRepository fileRepository, FileService fileService) {
        this.projectRepository = projectRepository;
        this.elasticsearchClient = elasticsearchClient;
        this.projectSearchRepository = projectSearchRepository;
        this.keywordRepository = keywordRepository;
        this.fileRepository = fileRepository;
        this.fileService = fileService;
    }

    @Override
    public List<ProjectSearchResponse> searchProjects(String searchText, List<String> keywords, String sortBy) {
        try {
            Query searchQuery = getSearchQuery(searchText, keywords);

            SortOptions sortOptions = SortOptions.of(sort -> {
                if ("date".equals(sortBy)) {
                    return sort.field(f -> f.field("creationDate").order(SortOrder.Asc));
                } else if ("name".equals(sortBy)) {
                    return sort.field(f -> f.field("name.keyword").order(SortOrder.Asc));
                }
                return sort.field(f -> f.field("_score").order(SortOrder.Desc));
            });

            SearchResponse<ProjectDocument> searchResponse = elasticsearchClient.search(s -> s
                            .index("projects")
                            .query(searchQuery)
                            .sort(sortOptions),
                    ProjectDocument.class
            );


            return searchResponse.hits().hits().stream()
                    .map(Hit::source)
                    .filter(Objects::nonNull)
                    .map(this::mapDocumentToSearchResponse)
                    .collect(Collectors.toList());

        } catch (IOException exception) {
            throw new RuntimeException("Failed to search projects", exception);
        }
    }

    private static Query getSearchQuery(String searchText, List<String> keywords) {
        BoolQuery.Builder boolQuery = new BoolQuery.Builder();

        if (searchText != null && !searchText.isBlank()) {
            boolQuery.must(m -> m
                    .matchPhrasePrefix(matchPhrasePrefix -> matchPhrasePrefix
                            .field("name")
                            .query(searchText)
                    )
            );
        }

        if (keywords != null && !keywords.isEmpty()) {
            boolQuery.filter(f -> f
                    .terms(terms -> terms
                            .field("keywords")
                            .terms(t -> t.value(keywords.stream().map(FieldValue::of).collect(Collectors.toList())))
                    )
            );
        }


        return new Query.Builder()
                .bool(boolQuery.build())
                .build();
    }

    @Override
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        Project project = mapToEntityByRequest(new Project(), projectRequest);
        project.setCreationDate(new Date());
        project.setUpdateDate(new Date());

        Project createdProjectEntity = projectRepository.save(project);
        projectSearchRepository.save(mapEntityToDocument(createdProjectEntity));

        return mapEntityToResponse(createdProjectEntity);
    }

    @Override
    public ProjectResponse updateProject(Long projectId, ProjectRequest projectRequest) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        Project updatedProject = mapToEntityByRequest(project, projectRequest);

        Project savedProject = projectRepository.save(updatedProject);
        updatedProject.setUpdateDate(new Date());

        projectSearchRepository.save(mapEntityToDocument(savedProject));

        return mapEntityToResponse(savedProject);
    }


    @Override
    public List<ProjectResponse> getProjects(String sortBy,String sortDirection) {
        String columnToSort = "";
        if(sortBy.equalsIgnoreCase("date")){
            columnToSort = "creationDate";
        } else {
            columnToSort = "name";
        }

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(columnToSort).ascending() : Sort.by(columnToSort).descending();
        List<Project> projects = projectRepository.findAll(sort);

        List<ProjectResponse> formattedProjects = new ArrayList<>();
        for (Project project : projects) {
            formattedProjects.add(mapEntityToResponse(project));
        }
        return formattedProjects;
    }

    @Override
    public ProjectResponse getProjectById(Long id) {
        Project model = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        return mapEntityToResponse(model);
    }

    @Override
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        List<File> files = fileRepository.findByProjectId(project.getId());
        for (File file : files) {
            fileService.deleteFileRecursively(projectId, file.getId());
        }

        project.getKeywords().forEach(keyword -> keyword.getProjects().remove(project));
        fileService.deleteProjectDir(projectId);
        projectRepository.delete(project);
    }

    private ProjectSearchResponse mapDocumentToSearchResponse(ProjectDocument document) {
        ProjectSearchResponse projectSearch = new ProjectSearchResponse();
        projectSearch.setId(document.getId());
        projectSearch.setName(document.getName());

        return projectSearch;
    }

    private ProjectDocument mapEntityToDocument(Project project) {
        ProjectDocument document = new ProjectDocument();
        document.setId(project.getId().toString());
        List<Long> keywords = Optional.ofNullable(project.getKeywords())
                .map(kws -> kws.stream().map(Keyword::getId).collect(Collectors.toList()))
                .orElse(null);

        document.setKeywords(keywords);

        document.setKeywords(keywords);
        document.setName(project.getName());
        document.setCreationDate(project.getCreationDate());

        return document;
    }

    private Project mapToEntityByRequest(Project project, ProjectRequest projectRequest) {
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

        List<Keyword> keywords = keywordRepository.findAllById(projectRequest.getKeywords());
        project.setKeywords(keywords);

        return project;
    }


    private ProjectResponse mapEntityToResponse(Project projectModel) {
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setId(projectModel.getId());
        projectResponse.setName(projectModel.getName());
        projectResponse.setCreationDate(projectModel.getCreationDate());
        projectResponse.setUpdateDate(projectModel.getUpdateDate());
        projectResponse.setExecutionStart(projectModel.getExecutionStart());
        projectResponse.setExecutionEnd(projectModel.getExecutionEnd());
        projectResponse.setRating(projectModel.getRating());
        projectResponse.setType(projectModel.getType());
        projectResponse.setPhase(projectModel.getPhase());
        projectResponse.setIsActive(projectModel.getIsActive());
        projectResponse.setAuditor(projectModel.getAuditor());
        projectResponse.setCode(projectModel.getCode());
        projectResponse.setInCooperation(projectModel.getInCooperation());

        List<KeywordResponse> keywords = projectModel.getKeywords().stream()
                .map(keyword -> new KeywordResponse(keyword.getId(), keyword.getName()))
                .collect(Collectors.toList());

        projectResponse.setKeywords(keywords);

        return projectResponse;
    }
}
