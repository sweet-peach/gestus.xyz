package xyz.gestus.gestus.dto;

import lombok.Data;

@Data
public class DirRequestDto {
    private String name;
    private Long parentId;
}
