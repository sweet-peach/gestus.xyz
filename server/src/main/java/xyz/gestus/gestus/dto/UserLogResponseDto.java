package xyz.gestus.gestus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogResponseDto {
    private long userId;
    private String userName;
    private long logCount;
}