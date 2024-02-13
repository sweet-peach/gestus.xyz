package xyz.gestus.gestus.feature.logs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogResponse {
    private long userId;
    private String userName;
    private long logCount;
}
