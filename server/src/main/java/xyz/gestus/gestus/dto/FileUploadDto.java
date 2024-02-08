package xyz.gestus.gestus.dto;

import lombok.Data;

@Data
public class FileUploadDto {
    private String name;
    private String type;
    private Long parentId;
}
