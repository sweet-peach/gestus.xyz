package xyz.gestus.gestus.feature.file.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FileResponse {
    private Long id;
    private String name;
    private String type;
    private Long size;
    private String path;
    private Date date;
    private Long parentId;
}
