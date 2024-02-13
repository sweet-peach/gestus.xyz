package xyz.gestus.gestus.feature.project.elastic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "projects")
public class ProjectDocument {
    @Id
    private String id;
    private String name;
    private List<Long> keywords;
    private Date creationDate;
}
