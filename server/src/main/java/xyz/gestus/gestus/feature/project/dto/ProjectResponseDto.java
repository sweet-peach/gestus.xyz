package xyz.gestus.gestus.feature.project.dto;

import lombok.Data;
import xyz.gestus.gestus.feature.keyword.dto.KeywordResponseDto;

import java.util.Date;
import java.util.List;

@Data
public class ProjectResponseDto {
    private Long id;
    private String name;
    private Date creationDate;
    private Date updateDate;
    private Date executionStart;
    private Date executionEnd;
    private int rating;
    private String type;
    private String phase;
    private Boolean isActive;
    private String auditor;
    private String code;
    private Boolean inCooperation;
    private List<KeywordResponseDto> keywords;
}
