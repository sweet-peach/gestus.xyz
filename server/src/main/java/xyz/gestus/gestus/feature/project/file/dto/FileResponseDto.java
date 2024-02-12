package xyz.gestus.gestus.feature.project.file.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FileResponseDto {
    private Long id;
    private String name;
    private String type;
    private Long size;
    private String path;
    private Date date;
    private Long parentId;
}
