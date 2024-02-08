package xyz.gestus.gestus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DirRequestDto {
    @NotEmpty
    private String name;
    private Long parentId;
}
