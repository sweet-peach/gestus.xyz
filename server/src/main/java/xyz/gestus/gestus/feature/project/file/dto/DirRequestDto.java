package xyz.gestus.gestus.feature.project.file.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DirRequestDto {
    @NotEmpty
    private String name;
    private Long parentId;
}
