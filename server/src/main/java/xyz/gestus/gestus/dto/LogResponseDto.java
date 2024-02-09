package xyz.gestus.gestus.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import xyz.gestus.gestus.models.UserModel;

import java.util.Date;

@Data
public class LogResponseDto{
    private String name;
    private Date date;
}
