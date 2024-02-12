package xyz.gestus.gestus.feature.project.file.dto;

import lombok.Data;

@Data
public class FileUploadDto {
    private String name;
    private String type;
    private Long parentId;
}
