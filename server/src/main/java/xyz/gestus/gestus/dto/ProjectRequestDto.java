package xyz.gestus.gestus.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ProjectRequestDto {
    private String name;
    private Date executionStart;
    private Date executionEnd;
    private int rating;
    private String type;
    private String phase;
    private Boolean isActive;
    private String auditor;
    private String code;
    private Boolean inCooperation;
    private List<Integer> keywords;
}
