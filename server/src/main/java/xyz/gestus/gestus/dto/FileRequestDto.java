package xyz.gestus.gestus.dto;

import lombok.Data;

@Data
public class FileRequestDto {
    private String name;
    private String type;
    private Long parentId;
}
