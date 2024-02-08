package xyz.gestus.gestus.dto;

import java.util.Date;
import java.util.List;

public class FileResponseDto {
    private Long id;
    private String name;
    private String type;
    private Long size;
    private String path;
    private Date date;
    private Long parentId;
    private List<Long> childs;
}
