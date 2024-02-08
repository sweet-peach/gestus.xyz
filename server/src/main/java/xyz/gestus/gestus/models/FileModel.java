package xyz.gestus.gestus.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "files")
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String type;
    Long size;
    String path;
    Date date;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectModel project;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private FileModel parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileModel> childs = new ArrayList<>();

    public void addChild(FileModel child) {
        childs.add(child);
        child.setParent(this);
    }

    public void removeChild(FileModel child) {
        childs.remove(child);
        child.setParent(null);
    }

}
