package xyz.gestus.gestus.feature.statistic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserLogCountResponse {
    private Long id;
    private String email;
    private long count;
}
