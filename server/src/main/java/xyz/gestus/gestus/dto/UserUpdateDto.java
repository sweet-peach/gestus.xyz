package xyz.gestus.gestus.dto;

import lombok.Data;
import xyz.gestus.gestus.models.Role;

import java.util.Optional;

@Data
public class UserUpdateDto {
    private Optional<String> firstName = Optional.empty();
    private Optional<String> lastName = Optional.empty();
    private Optional<String> email = Optional.empty();
    private Optional<String> password = Optional.empty();
    private Optional<Role> role = Optional.empty();
}
