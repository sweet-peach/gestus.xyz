package xyz.gestus.gestus.feature.keyword;

import jakarta.persistence.*;
import lombok.Data;
import xyz.gestus.gestus.feature.project.Project;

import java.util.List;

@Data
@Entity
@Table(name = "keywords")
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;

    @ManyToMany(mappedBy = "keywords")
    private List<Project> projects;
}
