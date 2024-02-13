package xyz.gestus.gestus.feature.project.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProjectSearchRepository extends ElasticsearchRepository<ProjectDocument, String> {
}
