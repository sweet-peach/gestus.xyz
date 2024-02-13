package xyz.gestus.gestus.feature.file.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DirRequest {
    @NotEmpty
    private String name;
    private Long parentId;
}
