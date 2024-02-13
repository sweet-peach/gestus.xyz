package xyz.gestus.gestus.feature.user.dto;

import lombok.Data;
import xyz.gestus.gestus.feature.user.Role;

import java.util.Optional;

@Data
public class UserUpdateRequest {
    private Optional<String> firstName = Optional.empty();
    private Optional<String> lastName = Optional.empty();
    private Optional<String> email = Optional.empty();
    private Optional<String> password = Optional.empty();
    private Optional<Role> role = Optional.empty();
}
