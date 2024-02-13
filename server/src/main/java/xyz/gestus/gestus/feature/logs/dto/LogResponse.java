package xyz.gestus.gestus.feature.logs.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LogResponse {
    private String name;
    private Date date;
}
