package xyz.gestus.gestus.feature.statistic.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExtensionCountResponse {
    private String extension;
    private long count;
}
