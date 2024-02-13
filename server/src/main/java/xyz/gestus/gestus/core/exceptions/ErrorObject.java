package xyz.gestus.gestus.core.exceptions;

import lombok.Data;

import java.util.Date;
@Data
public class ErrorObject {
    private Integer code;
    private String message;
}
