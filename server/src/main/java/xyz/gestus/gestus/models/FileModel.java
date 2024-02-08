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
    @JoinColumn(name = "parent_id")
    private FileModel parent;
}
