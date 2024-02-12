package xyz.gestus.gestus.feature.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ProjectRequestDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Execution start cannot be null")
    private Date executionStart;

    @NotNull(message = "Execution end cannot be null")
    private Date executionEnd;

    @NotNull(message = "Rating cannot be null")
    private int rating;

    @NotBlank(message = "Type cannot be empty")
    private String type;

    @NotBlank(message = "Phase cannot be empty")
    private String phase;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;

    @NotBlank(message = "Auditor cannot be empty")
    private String auditor;

    @NotBlank(message = "Code cannot be empty")
    private String code;

    @NotNull(message = "Cooperation status cannot be null")
    private Boolean inCooperation;

    private List<Long> keywords;
}
