package xyz.gestus.gestus.feature.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import xyz.gestus.gestus.feature.keyword.dto.KeywordResponse;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class ProjectResponse {
    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC")
    private LocalDateTime creationDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC")
    private LocalDateTime updateDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC")
    private LocalDateTime executionStart;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC")
    private LocalDateTime executionEnd;
    private int rating;
    private String type;
    private String phase;
    private Boolean isActive;
    private String auditor;
    private String code;
    private Boolean inCooperation;
    private List<String> keywords;
}
