package xyz.gestus.gestus.core.security;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import xyz.gestus.gestus.annotations.ValidRole;
import xyz.gestus.gestus.core.user.Role;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {
    private List<String> allowedRoles;

    @Override
    public void initialize(ValidRole constraintAnnotation) {
        this.allowedRoles = Stream.of(Role.values()).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return allowedRoles.contains(value);
    }
}
