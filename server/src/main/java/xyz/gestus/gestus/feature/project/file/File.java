package xyz.gestus.gestus.feature.project.file;

import jakarta.persistence.*;
import lombok.Data;
import xyz.gestus.gestus.feature.project.Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "files")
public class File {
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
    private Project project;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private File parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> childs = new ArrayList<>();

    public void addChild(File child) {
        childs.add(child);
        child.setParent(this);
    }

    public void removeChild(File child) {
        childs.remove(child);
        child.setParent(null);
    }

}
