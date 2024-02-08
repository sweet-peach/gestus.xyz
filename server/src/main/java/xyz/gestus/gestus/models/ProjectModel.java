package xyz.gestus.gestus.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "projects")
public class ProjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @Column(name = "creation_date")
    Date creationDate;
    @Column(name = "last_update_date")
    Date updateDate;
    @Column(name = "execution_start")
    Date executionStart;
    @Column(name = "execution_end")
    Date executionEnd;
    int rating;
    String type;
    String phase;
    @Column(name = "is_active")
    Boolean isActive;
    String auditor;
    String code;
    @Column(name = "in_cooperation")
    Boolean inCooperation;

    @ManyToMany
    @JoinTable(
            name = "project_keywords",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private List<KeywordModel> keywords;
}
